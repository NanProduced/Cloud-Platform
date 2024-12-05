package tech.nan.demo.mysql.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("organization")
public class OrganizationDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("org_name")
    private String orgName;

    @TableField("remark")
    private String remark;

    @TableField("group_id")
    private Long groupId;

    @TableField("manager_id")
    private Long managerId;
}
