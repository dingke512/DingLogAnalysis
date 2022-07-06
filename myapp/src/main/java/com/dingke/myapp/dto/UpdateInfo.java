package com.dingke.myapp.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor

public class UpdateInfo {
    private String userid;
    private String username;
    private String phone;
    private Integer gender;
    private Integer age;

}
