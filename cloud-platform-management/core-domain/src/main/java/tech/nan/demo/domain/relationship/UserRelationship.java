package tech.nan.demo.domain.relationship;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRelationship {

    private Long userId;

    private Long groupId;

    private LocalDateTime updateTime;
}
