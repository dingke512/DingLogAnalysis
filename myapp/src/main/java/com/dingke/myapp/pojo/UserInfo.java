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
@TableName("db_user_info")
@Accessors(chain = true)
public class UserInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userid;
    private String username;
    private Integer gender;
    private Integer age;
    private String phone;
    private String image;

}
