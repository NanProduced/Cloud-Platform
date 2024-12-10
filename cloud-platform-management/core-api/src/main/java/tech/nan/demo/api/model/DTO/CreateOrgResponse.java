package tech.nan.demo.api.model.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel("创建组织响应")
public class CreateOrgResponse {

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("组织管理员名称")
    private String managerName;

    @ApiModelProperty("组织管理员密码")
    private String password;

}
