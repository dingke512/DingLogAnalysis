package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ana_keyword_topn")
public class AnaKeyWordTop {

    private Integer id;
    private String keyword;
    private Integer count;
    private String date;
}
