package wang.penglei.user.service;

import io.jsonwebtoken.ExpiredJwtException;
import model.User;
import model.vo.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import utils.JwtUtils;
import wang.penglei.user.mapper.UserMapper;
import wang.penglei.user.utils.EmailUtil;
import wang.penglei.user.utils.SnaUtil;
import wang.penglei.user.vo.Token;

import java.time.LocalDateTime;

/**
 * @author Yuicon
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Mono<ResponseEntity> login(User user) {
        User findUser = userMapper.findByEmail(user.getEmail());
        if (findUser != null && findUser.getPassword().equals(SnaUtil.digest(user.getPassword()))) {
            return Mono.just(ResponseEntity.ok(JsonResponse.success("登录成功", Token.build(findUser))));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResponse.error("登录失败")));
    }

    public Mono<ResponseEntity> register(User user) {
        if (!EmailUtil.check(user.getEmail())) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("邮箱地址格式错误！")));
        }
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("用户名或密码不能为空！")));
        }
        user.setPassword(SnaUtil.digest(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        try {
            return Mono.justOrEmpty(ResponseEntity.ok(JsonResponse.success("注册成功", userMapper.insert(user))));
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("注册失败，邮箱已被注册！")));
        }
    }

    public Mono<ResponseEntity> refreshToken(@RequestBody Token token) {
        try {
            int id = Integer.parseInt(JwtUtils.parseToken(token.getRefreshToken()).getBody().getSubject());
            User user = userMapper.findById(id);
            return Mono.just(ResponseEntity.ok(JsonResponse.success("刷新成功", Token.build(user))));
        } catch (ExpiredJwtException e) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("Token 已超时")));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("刷新失败")));
    }

}
