package tech.nan.demo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.nan.demo.converter.CommonConverter;
import tech.nan.demo.domain.organization.Organization;
import tech.nan.demo.mysql.DO.OrganizationDO;
import tech.nan.demo.mysql.mapper.OrgMapper;
import tech.nan.demo.repository.OrganizationRepository;

@Repository
public class OrgRepositoryImpl extends ServiceImpl<OrgMapper, OrganizationDO> implements OrganizationRepository {

    @Autowired
    private CommonConverter converter;

    @Override
    public void createOrganization(Organization organization) {
        OrganizationDO organizationDO = converter.convert2OrgDO(organization);
        this.save(organizationDO);
    }
}
