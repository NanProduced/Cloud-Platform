package tech.nan.demo.auth.client.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.nan.demo.auth.client.dto.UserDTO;

public interface UserApi {

    String PREFIX = "/auth/user";

    @PostMapping(PREFIX + "/create")
    Long createUser(@RequestBody UserDTO userDTO);
}