package tech.nan.demo.auth.model.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GroupDO {

    @TableId(value = "id")
    private Long groupId;

    @TableField("group_name")
    private String groupName;

    @TableField("path")
    private String path;

}
