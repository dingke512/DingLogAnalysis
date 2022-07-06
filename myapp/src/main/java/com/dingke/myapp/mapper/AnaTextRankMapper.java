package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.pojo.AnaTextRank;
import com.dingke.myapp.vo.KeyValue;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnaTextRankMapper extends BaseMapper<AnaTextRank> {
    @Select("select word as name ,weight as value from ana_textrank where date= #{date} ")
    List<KeyValue> selectListByDay(String date);
}
