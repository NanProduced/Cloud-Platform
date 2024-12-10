package tech.nan.demo.application.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.repository.GroupRepository;
import tech.nan.demo.utils.InvocationContextHolder;

import java.util.Objects;

import static tech.nan.demo.utils.StringPool.PATH_FAN;

@Component
public class GroupPermissionValidator {

    @Autowired
    private GroupRepository groupRepository;

    /**
     * 判断组之间权限关系（根据path判断）
     * @param sourceGroupId
     * @param targetGroupId
     * @return
     */
    public boolean ifHasPermissionAtGroup(Long sourceGroupId, Long targetGroupId) {
        if (sourceGroupId <= 1) {
            return true;
        }
        String sourceGroupPath = Objects.requireNonNull(groupRepository.getGroupPath(sourceGroupId), "group " + sourceGroupId + "has no path");
        String targetGroupPath = Objects.requireNonNull(groupRepository.getGroupPath(targetGroupId), "group " + targetGroupId + "has no path");
        return  (sourceGroupPath.equals(targetGroupPath) || targetGroupPath.startsWith(sourceGroupPath + PATH_FAN));
    }

    public boolean ifHasPermissionAtUser(Long targetUserId) {
        Long sourceGroupId = InvocationContextHolder.getContext().getRequestUser().getGroupId();
        if (sourceGroupId <= 1) {
            return true;
        }
        Group sourceGroup = groupRepository.getGroupById(sourceGroupId);
        Group targetGroup = groupRepository.getGroupByUserId(targetUserId);
        return (sourceGroup.getPath().equals(targetGroup.getPath()) || sourceGroup.getPath().startsWith(targetGroup.getPath() + PATH_FAN));
    }
}
