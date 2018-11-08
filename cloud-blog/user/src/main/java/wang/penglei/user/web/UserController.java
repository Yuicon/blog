package wang.penglei.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
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

}
