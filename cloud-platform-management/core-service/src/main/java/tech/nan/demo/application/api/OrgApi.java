package tech.nan.demo.application.api;

import org.springframework.web.bind.annotation.PostMapping;
import tech.nan.demo.application.model.DTO.CreateOrgRequest;
import tech.nan.demo.application.model.DTO.CreateOrgResponse;

public interface OrgApi {

    String PREFIX = "/v2/org";

    @PostMapping(PREFIX + "/create")
    CreateOrgResponse createOrganization(CreateOrgRequest request);

}
