package tech.nan.demo.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import tech.nan.demo.auth.client.dto.UserDTO;

@FeignClient(value = "auth-server", contextId = "user-service")
public interface UserClient {

    String PREFIX = "/auth/user";

    @PostMapping(PREFIX + "/create")
    Long createUser(UserDTO userDTO);
}
