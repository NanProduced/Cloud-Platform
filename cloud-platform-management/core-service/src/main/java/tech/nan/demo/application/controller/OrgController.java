package tech.nan.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.nan.demo.application.api.OrgApi;
import tech.nan.demo.application.model.DTO.CreateOrgRequest;
import tech.nan.demo.application.model.DTO.CreateOrgResponse;
import tech.nan.demo.application.service.OrgService;

@RestController
public class OrgController implements OrgApi {

    @Autowired
    private OrgService orgService;

    @Override
    public CreateOrgResponse createOrganization(@RequestBody @Validated CreateOrgRequest request) {
        return orgService.createOrganization(request);
    }
}
