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
import wang.penglei.user.service.UserService;
import wang.penglei.user.vo.Token;

/**
 * @author Yuicon
 */
@RestController
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping()
    public Mono<JsonResponse> list() {
        return Mono.justOrEmpty(JsonResponse.success(userMapper.findAll()));
    }

    @PostMapping("public/register")
    public Mono<JsonResponse> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("public/login")
    public Mono<JsonResponse> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("public/refresh")
    public Mono<JsonResponse> refreshToken(@RequestBody Token token) {
        try {
            int id = Integer.parseInt(JwtUtils.parseToken(token.getRefreshToken()).getBody().getSubject());
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
