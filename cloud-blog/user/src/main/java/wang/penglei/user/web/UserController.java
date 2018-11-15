package wang.penglei.user.web;

import io.jsonwebtoken.ExpiredJwtException;
import model.User;
import model.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import utils.JwtUtils;
import wang.penglei.user.mapper.UserMapper;
import wang.penglei.user.vo.Token;

/**
 * @author Yuicon
 */
@RestController
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping()
    public Mono<JsonResponse> list() {
        return Mono.justOrEmpty(JsonResponse.success(userMapper.findAll()));
    }

    @PostMapping("public/register")
    public Mono<JsonResponse> register(@RequestBody User user) {
        return Mono.justOrEmpty(JsonResponse.success("注册成功", userMapper.insert(user)));
    }

    @PostMapping("public/login")
    public Mono<JsonResponse> login(@RequestBody User user) {
        User findUser = userMapper.findByUsername(user.getUsername());
        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
            return Mono.just(JsonResponse.success("登录成功", Token.build(findUser)));
        }
        return Mono.just(JsonResponse.error("登录失败"));
    }

    @PostMapping("public/login")
    public Mono<JsonResponse> refreshToken(@RequestBody String refreshToken) {
        try {
            int id = Integer.parseInt(JwtUtils.parseToken(refreshToken).getBody().getSubject());
            User user = userMapper.findById(id);
            return Mono.just(JsonResponse.success("刷新成功", Token.build(user)));
        } catch (ExpiredJwtException e) {
            return Mono.just(JsonResponse.error("Token 已超时"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.just(JsonResponse.error("登录失败"));
    }

}
