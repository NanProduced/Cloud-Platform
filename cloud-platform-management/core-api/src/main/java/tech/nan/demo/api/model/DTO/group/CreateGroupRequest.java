package tech.nan.demo.api.model.DTO.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("创建组请求")
public class CreateGroupRequest {

    @NotNull
    @ApiModelProperty("组名称")
    private String groupName;

    @ApiModelProperty("组描述")
    private String description;

    @NotNull
    @ApiModelProperty("父级组")
    private Long parent;

}
