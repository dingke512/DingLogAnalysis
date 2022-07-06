package com.dingke.myapp.vo;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class WordCountVo {
    private String date;
    private String keyword;
    private Integer count;
}
