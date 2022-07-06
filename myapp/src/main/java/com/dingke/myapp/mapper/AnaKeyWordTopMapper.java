package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.pojo.AnaKeyWordTop;
import com.dingke.myapp.vo.WordCountVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnaKeyWordTopMapper extends BaseMapper<AnaKeyWordTop> {
    @Select("SELECT date,keyword,max(count) as count FROM ana_keyword_topn WHERE date >= #{start} AND date <= #{end} GROUP BY date")
    List<WordCountVo> wordMax(String start, String end);
}
