package tech.nan.demo.auth.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("auth_url")
@Data
public class AuthUrlDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String url;

    private String authorization;
}
