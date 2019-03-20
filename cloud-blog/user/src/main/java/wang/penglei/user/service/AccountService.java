package wang.penglei.user.service;

import constant.UserStateConstant;
import io.jsonwebtoken.ExpiredJwtException;
import model.Account;
import model.vo.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

/**
 * @author Yuicon
 */
@Service
@Transactional(rollbackFor = {SQLException.class})
public class AccountService {

    private final AccountMapper accountMapper;
    private final MailService mailService;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private final Random random = new Random();
    private final String KEY = "ACTIVATE_KEY";
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(AccountMapper accountMapper, MailService mailService, ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.accountMapper = accountMapper;
        this.mailService = mailService;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    /**
     * 用户登录
     *
     * @param user    用户数据
     * @param request 请求
     * @return token
     */
    public Mono<ResponseEntity> login(Account user, ServerHttpRequest request) {
        Account findAccount = accountMapper.findByEmail(user.getEmail());
        if (findAccount == null ) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("不存在的用户")));
        }
        if (!findAccount.isAlright()) {
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("用户已删除或未激活")));
        }
        if (!findAccount.getPassword().equals(SnaUtil.digest(user.getPassword()))) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResponse.error("登录失败，账号或密码错误")));
        }
        findAccount.setLastLoginIP(Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress());
        findAccount.setLastLoginAt(LocalDateTime.now());
        findAccount.setLoginTimes(findAccount.getLoginTimes() + 1);
        accountMapper.update(findAccount);
        return Mono.just(ResponseEntity.ok(JsonResponse.success("登录成功", Token.build(findAccount))));
    }

    /**
     * 用户注册
     *
     * @param user    用户注册数据
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
            activateEmail(user);
            return Mono.justOrEmpty(ResponseEntity.ok(JsonResponse.success("注册成功", accountMapper.insert(user))));
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("注册失败，邮箱已被注册！")));
        }
    }

    /**
     * 激活邮箱
     *
     * @param email 待激活的邮箱
     * @param code  验证码
     * @return 激活结果
     */
    public Mono<ResponseEntity> activate(String email, String code) {
        String key = KEY + ":" + email;
        Account account = accountMapper.findByEmail(email);
        return reactiveRedisTemplate.hasKey(key).flatMap(aBoolean -> {
            if (!aBoolean) {
                return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("激活失败，找不到邮箱，验证码可能已过期！")));
            }
            return reactiveRedisTemplate.opsForValue().get(key).flatMap(s -> {
                if (!s.equals(code)) {
                    return Mono.just(ResponseEntity.badRequest().body(JsonResponse.error("激活失败，验证码不正确！")));
                }
                account.setState(UserStateConstant.ALRIGHT);
                accountMapper.update(account);
                return Mono.just(ResponseEntity.ok().body(JsonResponse.success("激活成功")));
            });
        });
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

    private void activateEmail(Account account) {
        String key = KEY + ":" + account.getEmail();
        String randomCode = randomCode();
        reactiveRedisTemplate.opsForValue().setIfAbsent(key, randomCode, Duration.ofMinutes(10)).subscribe(aBoolean -> {
            if (aBoolean) {
                mailService.sendSimpleMail(account.getEmail(), "ACG杀手账户注册邮箱验证", "尊敬的ACG杀手的用户：\n" +
                        "\n" +
                        "您好！\n" +
                        "\n" +
                        "欢迎使用ACG杀手，您本次注册的验证码为：\n" +
                        randomCode + "\n" +
                        "\n" +
                        "请将以上验证码输入注册页面中的验证码输入框内以完成注册；\n" +
                        "如非本人操作，请忽略。\n" +
                        "\n" +
                        "系统发信，请勿回复\n" +
                        "\n" +
                        "ACG杀手团队");
            } else {
                mailService.sendSimpleMail(account.getEmail(), "ACG杀手账户注册邮箱验证", "已发送验证码，请勿重复激活。");
            }
        });
    }

    private String randomCode() {
        StringBuilder data = new StringBuilder();
        random.ints(0, 9).limit(6).forEach(data::append);
        return data.toString();
    }

}
