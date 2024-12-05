package tech.nan.demo.domain.terminal;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Terminal {

    private Long terminalId;

    private String terminalName;

    private String description;

    private String password;

    private LocalDateTime createTime;

    private String model;

    private Integer status;

    private Long groupId;
}
