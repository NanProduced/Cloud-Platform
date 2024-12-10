package tech.nan.demo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tech.nan.demo.api.model.DTO.group.CreateGroupRequest;
import tech.nan.demo.api.model.DTO.group.UpdateGroupRequest;
import tech.nan.demo.api.model.VO.GroupTreeVO;

@Api(tags = "group")
public interface GroupApi {

    String PREFIX = "/group";

    @PostMapping(PREFIX + "/create")
    @ApiOperation("create group（创建组）")
    void createGroup(CreateGroupRequest request);

    @GetMapping(PREFIX + "/tree")
    @ApiOperation("get group tree（获取组树结构）")
    GroupTreeVO getGroupTree(Long groupId);

    @PostMapping(PREFIX + "/update")
    @ApiOperation("update group（编辑组信息）")
    void updateGroup(UpdateGroupRequest request);

    @PostMapping(PREFIX + "/delete")
    @ApiOperation("delete group（删除组）")
    void deleteGroup(Long groupId, Boolean force);
}
