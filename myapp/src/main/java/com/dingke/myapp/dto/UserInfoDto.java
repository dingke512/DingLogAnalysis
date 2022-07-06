package com.dingke.myapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private int age;
    private int gender;
    private String phone;
    private int priority;
    private String password;


}
