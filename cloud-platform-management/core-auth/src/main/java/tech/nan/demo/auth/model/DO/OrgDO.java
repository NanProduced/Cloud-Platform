package tech.nan.demo.auth.model.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrgDO {

    @TableId(value = "id")
    private Long orgId;

    @TableField("org_name")
    private String orgName;
}
