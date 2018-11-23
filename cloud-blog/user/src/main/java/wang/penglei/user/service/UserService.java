package wang.penglei.user.service;

import io.jsonwebtoken.ExpiredJwtException;
import model.User;
import model.vo.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
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

    public Mono<JsonResponse> login(User user) {
        User findUser = userMapper.findByEmail(user.getEmail());
        if (findUser != null && findUser.getPassword().equals(SnaUtil.digest(user.getPassword()))) {
            return Mono.just(JsonResponse.success("登录成功", Token.build(findUser)));
        }
        return Mono.just(JsonResponse.error("登录失败"));
    }

    public Mono<JsonResponse> register(User user) {
        if (!EmailUtil.check(user.getEmail())) {
            return Mono.just(JsonResponse.error("邮箱地址格式错误！"));
        }
        user.setPassword(SnaUtil.digest(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        try {
            return Mono.justOrEmpty(JsonResponse.success("注册成功", userMapper.insert(user)));
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            return Mono.justOrEmpty(JsonResponse.error("注册失败，邮箱已被注册！"));
        }
    }

    public Mono<JsonResponse> refreshToken(@RequestBody Token token) {
        try {
            int id = Integer.parseInt(JwtUtils.parseToken(token.getRefreshToken()).getBody().getSubject());
            User user = userMapper.findById(id);
            return Mono.just(JsonResponse.success("刷新成功", Token.build(user)));
        } catch (ExpiredJwtException e) {
            return Mono.just(JsonResponse.error("Token 已超时"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Mono.just(JsonResponse.error("刷新失败"));
    }

}
