package tech.nan.demo.mysql.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_group_rel")
public class UserGroupRelDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_group_id")
    private Long userGroupId;

    @TableField(value = "terminal_group_id")
    private Long terminalGroupId;
}
