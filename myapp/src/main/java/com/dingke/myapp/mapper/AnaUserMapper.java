package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.pojo.AnaUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnaUserMapper extends BaseMapper<AnaUser> {

    @Select("SELECT sum(total) from ana_user where date >= #{start} and date <= #{end}")
    Long SumTotal(String start, String end);

    @Select("SELECT date from ana_user where date >= #{start} and date <= #{end}")
    List<String> dateList(String start, String end);

    @Select("SELECT total from ana_user where date >= #{start} and date <= #{end}")
    List<Integer> TotalList(String start, String end);

    @Select("SELECT uv from ana_user where date >= #{start} and date <= #{end}")
    List<Integer> uvList(String start, String end);
}
