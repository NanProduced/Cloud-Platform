package tech.nan.demo.domain.relationship;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TerminalRelationship {

    private Long terminalId;

    private Long groupId;

    private LocalDateTime updateTime;
}
