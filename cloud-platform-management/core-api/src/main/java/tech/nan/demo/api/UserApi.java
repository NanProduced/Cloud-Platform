package tech.nan.demo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

@Api(tags = "user")
public interface UserApi {

    String PREFIX = "/user";

    @PostMapping(PREFIX + "/create")
    @ApiOperation("create user（创建用户）")
    void createUser();


}
