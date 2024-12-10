package tech.nan.demo.application.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.nan.demo.api.model.DTO.group.CreateGroupRequest;
import tech.nan.demo.api.model.DTO.group.UpdateGroupRequest;
import tech.nan.demo.api.model.VO.GroupTreeVO;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.exception.ExceptionEnum;
import tech.nan.demo.web.RequestUserInfo;
import tech.nan.demo.repository.GroupRepository;
import tech.nan.demo.utils.InvocationContextHolder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional(rollbackFor = Exception.class)
    public void createGroup(CreateGroupRequest request) {
        RequestUserInfo requestUser = InvocationContextHolder.getContext().getRequestUser();
        Group group = Group.builder()
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .creatorId(requestUser.getUserId())
                .parent(request.getParent())
                // 普通组
                .type(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        groupRepository.createNormalGroup(group);
    }

    public GroupTreeVO getGroupTree(Long groupId) {
        List<Group> allSubGroup = groupRepository.getAllSubGroupById(groupId);
        Map<Long, List<Group>> groupMapByParent = allSubGroup.stream().collect(Collectors.groupingBy(Group::getParent));
        Map<Long, String> groupNameMap = allSubGroup.stream().collect(Collectors.toMap(Group::getGroupId, Group::getGroupName));
        Group rootGroup = allSubGroup.get(0);
        GroupTreeVO rootGroupTree = GroupTreeVO.builder()
                .groupId(rootGroup.getGroupId())
                .groupName(rootGroup.getGroupName())
                .parent(rootGroup.getParent())
                .path(rootGroup.getPath())
                .hierarchy(generateGroupNamePath(rootGroup, groupNameMap))
                .build();
        buildTree(rootGroupTree, groupMapByParent, groupNameMap);
        return rootGroupTree;
    }

    private void buildTree(GroupTreeVO rootGroup, Map<Long, List<Group>> groupMapByParent, Map<Long, String> groupNameMap) {
        List<Group> subGroups = groupMapByParent.get(rootGroup.getGroupId());
        if (CollectionUtils.isEmpty(subGroups)) {
            return;
        }

        List<GroupTreeVO> subGroupTree = subGroups.stream().map(e -> {
            GroupTreeVO tree = new GroupTreeVO();
            tree.setGroupId(e.getGroupId());
            tree.setGroupName(e.getGroupName());
            tree.setParent(e.getParent());
            tree.setPath(e.getPath());
            tree.setHierarchy(generateGroupNamePath(e, groupNameMap));
            buildTree(tree, groupMapByParent, groupNameMap);
            return tree;
        }).collect(Collectors.toList());

        rootGroup.setSubGroups(subGroupTree);
    }

    private String generateGroupNamePath(Group group, Map<Long, String> groupNameMap) {
        return group.analysisPath().stream()
                .map(groupId -> groupNameMap.getOrDefault(groupId, "unknown"))
                .collect(Collectors.joining("|"));
    }

    public void updateGroup(UpdateGroupRequest request) {
        groupRepository.updateGroupById(request.getGroupId(), request.getGroupName(), request.getDescription());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(Long groupId) {
        // todo: 不能包含终端
        ExceptionEnum.GROUP_HAS_SUB_GROUP.throwIf(groupRepository.ifHasSubGroup(groupId), "group has sub group, need to be force delete.");
        groupRepository.deleteGroupById(groupId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteGroupAndSubGroup(Long groupId) {
        groupRepository.deleteGroupAndSubGroupById(groupId);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.getGroupById(groupId);
    }

    public Group getGroupByUserId(Long userId) {
        return groupRepository.getGroupByUserId(userId);
    }

    public Long getGroupIdByUserId(Long userId) {
        return groupRepository.getGroupIdByUserId(userId);
    }


}
