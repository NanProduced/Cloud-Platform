package tech.nan.demo.application.model.DTO;

import lombok.Data;

@Data
public class CreateGroupRequest {

    private String groupName;

    private String description;

    private Long parent;

}
