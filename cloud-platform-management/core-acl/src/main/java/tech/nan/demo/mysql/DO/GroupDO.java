package tech.nan.demo.mysql.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("terminal_group")
public class GroupDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long groupId;

    @TableField("group_name")
    private String groupName;

    @TableField("description")
    private String description;

    @TableField("creator_id")
    private Long creatorId;

    @TableField("parent")
    private Long parent;

    @TableField("path")
    private String path;

    @TableField("type")
    private Integer type;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
