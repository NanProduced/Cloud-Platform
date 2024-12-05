package tech.nan.demo.domain.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class User {

    private Long userId;

    private String username;

    private String email;

    private String phone;

    private List<String> roles;

    private List<String> authorities;

    private Integer status;

    /**
     * 用户类型
     * 0：全局管理员Superroot
     * 1：organization管理员
     * 2：普通账户
     */
    private Integer type;

    private Long creatorId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
