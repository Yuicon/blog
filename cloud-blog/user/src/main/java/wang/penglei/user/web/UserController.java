package wang.penglei.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wang.penglei.user.mapper.UserMapper;
import wang.penglei.user.model.User;

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

    @PostMapping()
    public Mono<Integer> register(@RequestBody User user) {
        return Mono.justOrEmpty(userMapper.insert(user));
    }

    @PostMapping()
    public Mono<String> login(@RequestBody User user) {
        User findUser = userMapper.findByUsername(user.getUsername());
        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
            return Mono.just("jwt");
        }
        return Mono.just("登录失败");
    }

}
