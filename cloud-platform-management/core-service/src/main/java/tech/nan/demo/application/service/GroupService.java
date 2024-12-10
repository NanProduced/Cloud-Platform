package tech.nan.demo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.nan.demo.application.model.DTO.CreateGroupRequest;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.http.RequestUserInfo;
import tech.nan.demo.repository.GroupRepository;
import tech.nan.demo.utils.InvocationContextHolder;

import java.time.LocalDateTime;
import java.util.List;


public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public void createGroup(CreateGroupRequest request) {
        RequestUserInfo requestUser = InvocationContextHolder.getContext().getRequestUser();
        Group group = Group.builder()
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .creatorId(requestUser.getUserId())
                .parent(request.getParent())
                .type(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        groupRepository.createNormalGroup(group);
    }

    public List<Group> getGroupListById(Long groupId) {
        List<Group> allGroups = groupRepository.getAllSubGroupById(groupId);
        // other
        return allGroups;
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.getGroupById(groupId);
    }
}
