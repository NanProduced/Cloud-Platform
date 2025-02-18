package tech.nan.demo.application.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOrgRequest {

    @NotNull
    private String orgName;

    private String remark;

    @NotNull
    private String rootUserName;

    private String mail;

}
