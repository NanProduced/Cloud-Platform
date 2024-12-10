package tech.nan.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.nan.demo.api.OrgApi;
import tech.nan.demo.api.model.DTO.CreateOrgRequest;
import tech.nan.demo.api.model.DTO.CreateOrgResponse;
import tech.nan.demo.application.service.OrgService;
import tech.nan.demo.common.RequiredAuthorization;

@RestController
public class OrgController implements OrgApi {

    @Autowired
    private OrgService orgService;

    @Override
    @RequiredAuthorization("ONLY_SUPER_ROOT")
    public CreateOrgResponse createOrganization(@RequestBody @Validated CreateOrgRequest request) {
        return orgService.createOrganization(request);
    }
}
