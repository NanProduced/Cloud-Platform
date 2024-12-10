package tech.nan.demo.web;

import lombok.Data;

import java.util.List;

@Data
public class RequestUserInfo {

    private Long userId;

    private String username;

    private List<String> authorities;

    private List<String> userRoles;

    private Long groupId;

    private String groupName;

    private Long orgId;

    private String orgName;
}
