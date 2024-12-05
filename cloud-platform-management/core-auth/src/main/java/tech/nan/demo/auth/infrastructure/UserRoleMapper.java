package tech.nan.demo.auth.infrastructure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.nan.demo.auth.model.DO.UserRoleDO;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    List<UserRoleDO> selectUserRolesByCode(@Param("roles") Integer roles);
}
