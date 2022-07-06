package com.dingke.myapp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ana_user_topn")
public class AnaUserTop {
    private Integer Id;
    private String Userid;
    private Integer Count;
    private String Date;
}
