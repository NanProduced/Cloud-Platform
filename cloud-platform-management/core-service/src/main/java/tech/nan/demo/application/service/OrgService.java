package tech.nan.demo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.nan.demo.application.model.DTO.CreateOrgRequest;
import tech.nan.demo.application.model.DTO.CreateOrgResponse;
import tech.nan.demo.auth.client.UserClient;
import tech.nan.demo.auth.client.dto.UserDTO;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.domain.organization.Organization;
import tech.nan.demo.repository.GroupRepository;
import tech.nan.demo.repository.OrganizationRepository;
import tech.nan.demo.utils.PasswordUtils;

import java.time.LocalDateTime;
import java.util.List;

public class OrgService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private OrganizationRepository orgRepository;

    @Transactional
    public CreateOrgResponse createOrganization(CreateOrgRequest request) {
        // todo：鉴权、校验
        Group rootGroup = Group.builder()
                .groupName(request.getOrgName())
                .description("organization root group")
                .parent(0L)
                .creatorId(1L)
                .type(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        Group orgRootGroup = groupRepository.createOrgRootGroup(rootGroup);
        String password = PasswordUtils.generatePassword(12);
        UserDTO orgManager = UserDTO.builder()
                .groupId(rootGroup.getGroupId())
                .username(request.getRootUserName())
                .password(password)
                .email(request.getMail())
                .roles(List.of(2))
                .type(1)
                .status(0)
                .creatorId(1L)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        Long userId = userClient.createUser(orgManager);
        Organization organization = Organization.builder()
                .groupId(orgRootGroup.getGroupId())
                .managerId(userId)
                .orgName(request.getOrgName())
                .remark(request.getRemark())
                .build();
        orgRepository.createOrganization(organization);
        return CreateOrgResponse.builder()
                .orgName(request.getOrgName())
                .managerName(request.getOrgName())
                .password(password)
                .build();
    }
}
