package wang.penglei.user.web;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import utils.JwtUtils;
import wang.penglei.user.mapper.UserMapper;

import java.util.List;

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
    public Mono<List<User>> list() {
        return Mono.justOrEmpty(userMapper.findAll());
    }

    @PostMapping("public/register")
    public Mono<Integer> register(@RequestBody User user) {
        return Mono.justOrEmpty(userMapper.insert(user));
    }

    @PostMapping("public/login")
    public Mono<String> login(@RequestBody User user) {
        User findUser = userMapper.findByUsername(user.getUsername());
        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
            return Mono.just(JwtUtils.buildToken(findUser));
        }
        return Mono.just("登录失败");
    }

}
