package tech.nan.demo.api.model.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("创建组织请求")
public class CreateOrgRequest {

    @NotNull
    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("组织备注")
    private String remark;

    @NotNull
    @ApiModelProperty("组织管理员名称")
    private String rootUserName;

    @ApiModelProperty("组织绑定邮箱")
    private String mail;

}
