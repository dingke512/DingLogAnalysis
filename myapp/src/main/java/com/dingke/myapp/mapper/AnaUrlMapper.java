package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.pojo.AnaUrl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnaUrlMapper extends BaseMapper<AnaUrl> {
    @Select("select page_count from ana_url where date>= #{start} and date<= #{end}")
    List<Integer> PageCountList(String start,String end);
}
