package wang.penglei.user.service;

import io.jsonwebtoken.ExpiredJwtException;
import model.Account;
import model.vo.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import utils.JwtUtils;
import wang.penglei.user.mapper.AccountMapper;
import wang.penglei.user.utils.EmailUtil;
import wang.penglei.user.utils.SnaUtil;
import wang.penglei.user.vo.Token;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuicon
 */
@Service
@Transactional(rollbackFor = {SQLException.class})
public class AccountService {

    private final AccountMapper accountMapper;
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * 用户登录
     *
     * @param user 用户数据
     * @param request 请求
     * @return token
     */
    public Mono<ResponseEntity> login(Account user, ServerHttpRequest request) {
        Account findAccount = accountMapper.findByEmail(user.getEmail());
        if (findAccount != null && findAccount.getPassword().equals(SnaUtil.digest(user.getPassword()))) {
            findAccount.setLastLoginIP(Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress());
            findAccount.setLastLoginAt(LocalDateTime.now());
            findAccount.setLoginTimes(findAccount.getLoginTimes() + 1);
            accountMapper.update(findAccount);
            return Mono.just(ResponseEntity.ok(JsonResponse.success("登录成功", Token.build(findAccount))));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResponse.error("登录失败")));
    }

    /**
     * 用户注册
     *
     * @param user 用户注册数据
     * @param request 请求
     * @return sql影响记录数
     */
    public Mono<ResponseEntity> register(Account user, ServerHttpRequest request) {
        if (!EmailUtil.check(user.getEmail())) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("邮箱地址格式错误！")));
        }
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("用户名或密码不能为空！")));
        }
        user.setPassword(SnaUtil.digest(user.getPassword()));
        user.setCreateAtIP(Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress());
        try {
            return Mono.justOrEmpty(ResponseEntity.ok(JsonResponse.success("注册成功", accountMapper.insert(user))));
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("注册失败，邮箱已被注册！")));
        }
    }

    /**
     * 刷新token
     *
     * @param token 旧token
     * @return 新token
     */
    @Transactional(readOnly = true, rollbackFor = {SQLException.class})
    public Mono<ResponseEntity> refreshToken(@RequestBody Token token) {
        try {
            int id = Integer.parseInt(JwtUtils.parseToken(token.getRefreshToken()).getBody().getSubject());
            Account user = accountMapper.findById(id);
            return Mono.just(ResponseEntity.ok(JsonResponse.success("刷新成功", Token.build(user))));
        } catch (ExpiredJwtException e) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("Token 已超时")));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("刷新失败")));
    }

}
