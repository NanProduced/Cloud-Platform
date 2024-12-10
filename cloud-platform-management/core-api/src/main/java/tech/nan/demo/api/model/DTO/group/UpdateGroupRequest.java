package tech.nan.demo.api.model.DTO.group;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("编辑组请求")
public class UpdateGroupRequest {

    @NotNull
    private Long groupId;

    private String groupName;

    private String description;

}
