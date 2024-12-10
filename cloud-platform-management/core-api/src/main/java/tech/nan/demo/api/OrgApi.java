package tech.nan.demo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import tech.nan.demo.api.model.DTO.CreateOrgRequest;
import tech.nan.demo.api.model.DTO.CreateOrgResponse;

@Api(tags = "organization")
public interface OrgApi {

    String PREFIX = "/org";

    @PostMapping(PREFIX + "/create")
    @ApiOperation("create organization（创建组织）")
    CreateOrgResponse createOrganization(CreateOrgRequest request);

}
