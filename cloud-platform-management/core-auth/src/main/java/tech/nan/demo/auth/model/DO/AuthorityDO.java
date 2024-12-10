package tech.nan.demo.auth.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("authority")
@Data
public class AuthorityDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String authority;

    private Integer code;

    @TableField("create_time")
    @JsonIgnore
    private LocalDateTime createTime;

}
