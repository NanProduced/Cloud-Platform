package tech.nan.demo.application.model.DTO;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;

    private String password;

    private Long groupId;
}
