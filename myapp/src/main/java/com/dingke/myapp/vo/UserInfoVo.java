package com.dingke.myapp.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoVo {
    private String userid;
    private String password;
    private String username;
    private Integer gender;
    private Integer age;
    private String phone;
    private String image;
    private String priority;


}
