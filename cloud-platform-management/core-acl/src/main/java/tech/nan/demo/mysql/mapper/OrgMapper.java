package tech.nan.demo.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.nan.demo.mysql.DO.OrganizationDO;

@Mapper
public interface OrgMapper extends BaseMapper<OrganizationDO> {
}
