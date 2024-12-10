package tech.nan.demo.mysql.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("terminal_rel")
@Data
public class TerminalRelDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("terminal_id")
    private Long terminalId;

    @TableField("group_id")
    private Long groupId;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
