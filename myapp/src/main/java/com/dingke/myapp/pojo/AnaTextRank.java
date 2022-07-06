package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ana_textrank")
public class AnaTextRank {
    private Integer id;
    private String date;
    private String word;
    private Float weight;

}
