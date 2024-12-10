package tech.nan.demo.auth.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_role")
public class UserRoleDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer code;

    @TableField("role_name")
    private String roleName;

    private Integer authorities;

    /**
     * 0：系统角色
     * 1：自定义角色
     */
    private Integer type;

    @TableField("creator_id")
    private Integer creatorId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
