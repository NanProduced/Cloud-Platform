package tech.nan.demo.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.nan.demo.mysql.DO.TerminalDO;

@Mapper
public interface TerminalMapper extends BaseMapper<TerminalDO> {
}
