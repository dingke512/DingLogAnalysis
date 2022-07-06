package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ana_user")
public class AnaUser {
    private Integer Id;
    private Integer uv;
    private Float UserAvg;
    private Integer Total;
    private String Date;
}
