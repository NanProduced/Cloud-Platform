package tech.nan.demo.repository;

import tech.nan.demo.domain.group.Group;

import java.util.List;

public interface GroupRepository {

    Group createOrgRootGroup(Group group);

    Group createNormalGroup(Group group);

    Group getGroupById(Long groupId);

    String getGroupPath(Long groupId);

    List<Group> getAllSubGroupById(Long groupId);

    boolean ifHasSubGroup(Long groupId);

    Group getGroupByUserId(Long userId);

    Long getGroupIdByUserId(Long userId);

    Group getGroupByTerminalId(Long terminalId);

    void updateGroupById(Long groupId, String groupName, String description);

    void deleteGroupById(Long groupId);

    void deleteGroupAndSubGroupById(Long groupId);
}

