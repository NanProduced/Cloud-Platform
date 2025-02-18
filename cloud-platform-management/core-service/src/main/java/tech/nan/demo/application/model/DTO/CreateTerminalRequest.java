package tech.nan.demo.application.model.DTO;

import lombok.Data;

@Data
public class CreateTerminalRequest {

    private String terminalName;

    private Long groupId;

    private String description;
}
