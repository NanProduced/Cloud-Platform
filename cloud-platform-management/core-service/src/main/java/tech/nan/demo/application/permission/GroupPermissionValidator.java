package tech.nan.demo.application.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.nan.demo.repository.GroupRepository;

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
    public boolean ifHasPermission(Long sourceGroupId, Long targetGroupId) {
        String sourceGroupPath = Objects.requireNonNull(groupRepository.getGroupPath(sourceGroupId), "group " + sourceGroupId + "has no path");
        String targetGroupPath = Objects.requireNonNull(groupRepository.getGroupPath(targetGroupId), "group " + targetGroupId + "has no path");
        return  (sourceGroupPath.equals(targetGroupPath) || sourceGroupPath.startsWith(targetGroupPath + PATH_FAN));
    }
}
