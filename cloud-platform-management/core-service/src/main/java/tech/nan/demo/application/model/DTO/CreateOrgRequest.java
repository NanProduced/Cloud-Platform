package tech.nan.demo.application.model.DTO;

import lombok.Data;

@Data
public class CreateOrgRequest {

    private String orgName;

    private String remark;

    private String rootUserName;

    private String mail;

}
