package wang.penglei.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wang.penglei.user.mapper.UserMapper;
import wang.penglei.user.model.User;
import wang.penglei.user.utils.JwtUtils;

import java.util.List;

/**
 * @author Yuicon
 */
@RestController
public class UserController {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserMapper userMapper, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }


    @GetMapping()
    public Mono<List<User>> list() {
        return Mono.justOrEmpty(userMapper.findAll());
    }

    @PostMapping("register")
    public Mono<Integer> register(@RequestBody User user) {
        return Mono.justOrEmpty(userMapper.insert(user));
    }

    @PostMapping("login")
    public Mono<String> login(@RequestBody User user) {
        User findUser = userMapper.findByUsername(user.getUsername());
        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
            return Mono.just(jwtUtils.buildToken(findUser));
        }
        return Mono.just("登录失败");
    }

}
