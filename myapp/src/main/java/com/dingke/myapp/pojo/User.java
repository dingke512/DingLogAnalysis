package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("db_user")
@Accessors(chain = true)
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userid;
    private String password;
    private Integer priority;
}
