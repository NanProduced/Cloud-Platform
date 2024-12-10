package tech.nan.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.nan.demo.api.GroupApi;
import tech.nan.demo.api.model.DTO.group.CreateGroupRequest;
import tech.nan.demo.api.model.DTO.group.UpdateGroupRequest;
import tech.nan.demo.api.model.VO.GroupTreeVO;
import tech.nan.demo.application.permission.GroupPermissionValidator;
import tech.nan.demo.application.service.GroupService;
import tech.nan.demo.common.RequiredAuthorization;
import tech.nan.demo.exception.ExceptionEnum;
import tech.nan.demo.utils.InvocationContextHolder;

import java.util.Objects;

@RestController
public class GroupController implements GroupApi {

    @Autowired
    private GroupPermissionValidator permissionValidator;

    @Autowired
    private GroupService groupService;

    @Override
    @RequiredAuthorization("GROUP_CREATE")
    public void createGroup(@RequestBody @Validated CreateGroupRequest request) {
        Long groupId = InvocationContextHolder.getGroupId();
        ExceptionEnum.PERMISSION_DENIED.throwIf(!permissionValidator.ifHasPermissionAtGroup(groupId, request.getParent()));
        groupService.createGroup(request);
    }

    @Override
    @RequiredAuthorization("DEFAULT")
    public GroupTreeVO getGroupTree(@RequestParam(value = "groupId", required = false) Long groupId) {
        Long rootGroupId = InvocationContextHolder.getGroupId();
        if (Objects.nonNull(groupId)) {
            ExceptionEnum.PERMISSION_DENIED.throwIf(!permissionValidator.ifHasPermissionAtGroup(rootGroupId, groupId));
            return groupService.getGroupTree(groupId);
        }
        else {
            return groupService.getGroupTree(rootGroupId);
        }
    }

    @Override
    @RequiredAuthorization("GROUP_UPDATE")
    public void updateGroup(@RequestBody @Validated UpdateGroupRequest request) {
        Long groupId = InvocationContextHolder.getGroupId();
        ExceptionEnum.PERMISSION_DENIED.throwIf(!permissionValidator.ifHasPermissionAtGroup(groupId, request.getGroupId()));
        groupService.updateGroup(request);
    }

    @Override
    @RequiredAuthorization("GROUP_DELETE")
    public void deleteGroup(@RequestParam("groupId") Long groupId,
                            @RequestParam(value = "force", required = false) Boolean force) {
        Long currentGroupId = InvocationContextHolder.getGroupId();
        ExceptionEnum.PERMISSION_DENIED.throwIf(!permissionValidator.ifHasPermissionAtGroup(currentGroupId, groupId));
        if (Objects.nonNull(force) && force) {
            groupService.deleteGroupAndSubGroup(groupId);
        }
        else {
            groupService.deleteGroup(groupId);
        }
    }
}
