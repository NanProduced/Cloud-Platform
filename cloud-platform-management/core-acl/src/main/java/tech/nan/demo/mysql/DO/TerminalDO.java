package tech.nan.demo.mysql.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TerminalDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long terminalId;

    @TableField(value = "terminal_name")
    private String terminalName;

    @TableField(value = "description")
    private String description;

    @TableField(value = "password")
    private String password;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField("model")
    private String model;

    @TableField("status")
    private Integer status;

    @TableField("group_id")
    private Long groupId;

}
