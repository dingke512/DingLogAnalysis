package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ana_keyword")
public class AnaKeyWord {
    private Integer id;
    private Integer wordCount;
    private Float wordAvg;
    private Float avgLength;
    private String date;
}
