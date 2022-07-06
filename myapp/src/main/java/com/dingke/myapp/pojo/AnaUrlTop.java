package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ana_url_topn")
public class AnaUrlTop {
    private Integer Id;
    private String Url;
    private Integer Count;
    private String Date;
}
