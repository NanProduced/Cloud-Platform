package tech.nan.demo.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import tech.nan.demo.auth.client.api.UserApi;

@FeignClient(value = "auth-server", contextId = "user-service")
public interface UserClient extends UserApi {

}
