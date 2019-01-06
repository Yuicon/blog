package wang.penglei.user.web;

import model.User;
import model.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
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
        return userService.refreshToken(token);
    }

}
