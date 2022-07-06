package com.dingke.myapp.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TrendVo {

    private List<String> date;
    private List<Integer> total;
    private List<Integer> uv;
    private List<Integer> url_count;
    private List<Integer> word_count;
}
