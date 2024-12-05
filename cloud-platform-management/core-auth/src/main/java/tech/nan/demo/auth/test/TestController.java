package tech.nan.demo.auth.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.nan.demo.auth.infrastructure.UserRoleMapper;
import tech.nan.demo.auth.model.UserPrincipal;
import tech.nan.demo.auth.service.UserServiceImpl;

@RestController
public class TestController {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenEndpoint tokenEndpoint;

    @GetMapping("/v2/users")
    public UserPrincipal getUser(@RequestParam("name") String username) {
        return (UserPrincipal) userService.loadUserByUsername(username);
    }

}
