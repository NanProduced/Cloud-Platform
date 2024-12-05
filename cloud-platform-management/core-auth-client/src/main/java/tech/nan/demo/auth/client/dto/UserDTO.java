package tech.nan.demo.auth.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDTO {

    private Long groupId;

    private String username;

    private String password;

    private String email;

    private String phone;

    private List<Integer> roles;

    private Integer status;

    private Integer type;

    private Long creatorId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
