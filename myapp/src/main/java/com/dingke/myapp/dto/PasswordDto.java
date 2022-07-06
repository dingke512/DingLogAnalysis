package com.dingke.myapp.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PasswordDto {
    private String phone;
    private String pwd;
    private String npwd;
}
