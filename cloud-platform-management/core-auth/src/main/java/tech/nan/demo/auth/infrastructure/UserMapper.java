package tech.nan.demo.auth.infrastructure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.nan.demo.auth.model.DO.UserDO;
import tech.nan.demo.auth.model.DO.UserRelDO;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    void insertUserRel(@Param("userRel")UserRelDO userRelDO);

}