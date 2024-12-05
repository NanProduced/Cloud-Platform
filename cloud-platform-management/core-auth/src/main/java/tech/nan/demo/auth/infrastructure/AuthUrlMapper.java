package tech.nan.demo.auth.infrastructure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.nan.demo.auth.model.DO.AuthUrlDO;

@Mapper
public interface AuthUrlMapper extends BaseMapper<AuthUrlDO> {
}
