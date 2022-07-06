package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ana_url")
public class AnaUrl {
    private Integer Id;
    private Integer PageCount;
    private Float PageAvg;
    private String Date;
}
