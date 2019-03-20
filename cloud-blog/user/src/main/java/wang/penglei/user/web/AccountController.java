package wang.penglei.user.web;

import model.Account;
import model.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import wang.penglei.user.mapper.AccountMapper;
import wang.penglei.user.service.AccountService;
import wang.penglei.user.vo.Token;

/**
 * @author Yuicon
 */
@RestController
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @GetMapping()
    public Mono<JsonResponse> list() {
        return Mono.justOrEmpty(JsonResponse.success(accountMapper.findAll()));
    }

    @PostMapping("public/register")
    public Mono<ResponseEntity> register(ServerHttpRequest request, @RequestBody Account user) {
        return accountService.register(user, request);
    }

    @PostMapping("public/login")
    public Mono<ResponseEntity> login(@RequestBody Account user, ServerHttpRequest request) {
        return accountService.login(user, request);
    }

    @PostMapping("public/refresh")
    public Mono<ResponseEntity> refreshToken(@RequestBody Token token) {
        return accountService.refreshToken(token);
    }

}
