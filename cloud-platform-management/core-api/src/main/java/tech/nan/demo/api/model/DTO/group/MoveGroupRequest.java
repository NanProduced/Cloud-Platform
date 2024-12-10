package tech.nan.demo.api.model.DTO.group;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("移动组请求")
public class MoveGroupRequest {


    private Long sourceGroupId;

    private Long targetGroupId;
}
