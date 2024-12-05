package tech.nan.demo.domain.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organization {

    private Long orgId;

    private String orgName;

    private String remark;

    private Long groupId;

    private Long managerId;

}
