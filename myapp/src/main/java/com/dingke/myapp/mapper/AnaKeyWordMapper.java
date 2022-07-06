package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.pojo.AnaKeyWord;
import com.dingke.myapp.vo.WordAvgVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnaKeyWordMapper extends BaseMapper<AnaKeyWord> {
    @Select("SELECT avg_length,date from ana_keyword  where date >= #{start} and date <= #{end} ")
    List<Float> avgLength(String start, String end);


    @Select("select word_count from ana_keyword where date>= #{start} and date<= #{end}")
    List<Integer> WordCountList(String start,String end);
}
