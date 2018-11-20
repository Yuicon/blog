package wang.penglei.user.service;

import model.User;
import model.vo.JsonResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wang.penglei.user.mapper.UserMapper;
import wang.penglei.user.utils.EmailUtil;
import wang.penglei.user.utils.SnaUtil;
import wang.penglei.user.vo.Token;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * @author Yuicon
 */
@Service
public class UserService {

    private final UserMapper userMapper;

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
        return Mono.justOrEmpty(JsonResponse.success("注册成功", userMapper.insert(user)));
    }

}
