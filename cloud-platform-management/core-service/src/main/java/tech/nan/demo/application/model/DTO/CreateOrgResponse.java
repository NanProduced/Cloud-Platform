package tech.nan.demo.application.model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrgResponse {

    private String orgName;

    private String managerName;

    private String password;

}
