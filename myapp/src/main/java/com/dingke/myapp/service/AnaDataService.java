package com.dingke.myapp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingke.myapp.mapper.*;
import com.dingke.myapp.pojo.*;
import com.dingke.myapp.vo.KeyValue;
import com.dingke.myapp.vo.TrendVo;
import com.dingke.myapp.vo.WordCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;


@Service
@Slf4j
public class AnaDataService {

    @Autowired
    private AnaUserMapper anaUserMapper;

    @Autowired
    private AnaUserTopMapper anaUserTopMapper;

    @Autowired
    private AnaUrlTopMapper anaUrlTopMapper;

    @Autowired
    private AnaUrlMapper anaUrlMapper;

    @Autowired
    private AnaKeyWordMapper anaKeyWordMapper;

    @Autowired
    private  AnaKeyWordTopMapper anaKeyWordTopMapper;

    @Autowired
    private  AnaTextRankMapper anaTextRankMapper;


    public Map<String,Object>AnaTrendService(String start,String end){
        //todo trend
        List<String> dateList = anaUserMapper.dateList(start, end);
        List<Integer> uvList = anaUserMapper.uvList(start, end);
        List<Integer> TotalList = anaUserMapper.TotalList(start, end);
        List<Integer> UrlCountList = anaUrlMapper.PageCountList(start, end);
        List<Integer>WordCountList = anaKeyWordMapper.WordCountList(start, end);
        Long SumTotal= anaUserMapper.SumTotal(start,end);
        List<WordCountVo>  wordMax = anaKeyWordTopMapper.wordMax(start,end);
        List<Float> dataY = anaKeyWordMapper.avgLength(start,end);
        TrendVo trendVo = new TrendVo();
        trendVo.setDate(dateList).setTotal(TotalList).setUrl_count(UrlCountList).setUv(uvList).setWord_count(WordCountList);
        Map<String,Object> result = new HashMap<>();
        result.put("trend",trendVo);
        result.put("sum_total",SumTotal);
        result.put("word_data",wordMax);
        result.put("data_x",dateList);
        result.put("data_y",dataY);
        return result;
    }



    public Map<String,Object> AnaUserService( String data, int n){
        QueryWrapper<AnaUserTop> wrapper = new QueryWrapper<>();
        wrapper.eq("date",data).orderByDesc("count").last("limit "+n);
        List<AnaUserTop> list = anaUserTopMapper.selectList(wrapper);
        AnaUser anaUser = anaUserMapper.selectOne(new QueryWrapper<AnaUser>().eq("date",data));
        Map<String,Object> map = new HashMap<>();
        map.put("user_top",list);
        map.put("user_avg",anaUser.getUserAvg());
        map.put("total",anaUser.getTotal());
        map.put("user_count",anaUser.getUv());
        return map;
    }


    public Map<String,Object> AnaUrlService( String data, int n){
        QueryWrapper<AnaUrlTop> wrapper = new QueryWrapper<>();
        wrapper.eq("date",data).orderByDesc("count").last("limit "+n);
        // topk
        List<AnaUrlTop> urlTopList = anaUrlTopMapper.selectList(wrapper);
        // total
        AnaUser anaUser = anaUserMapper.selectOne(new QueryWrapper<AnaUser>().eq("date",data));
        // anaurl
        AnaUrl anaUrl = anaUrlMapper.selectOne(new QueryWrapper<AnaUrl>().eq("date",data));
        Map<String,Object> map = new HashMap<>();
        map.put("url_top",urlTopList);
        map.put("url_avg",anaUrl.getPageAvg());
        map.put("total",anaUser.getTotal());
        map.put("url_count",anaUrl.getPageCount());
        return map;
    }



    public Map<String,Object> AnaKeyWordsService( String data, int n){
        QueryWrapper<AnaKeyWordTop> wrapper = new QueryWrapper<>();
        wrapper.eq("date",data).orderByDesc("count").last("limit "+n);
        // topk
        List<AnaKeyWordTop> urlTopList = anaKeyWordTopMapper.selectList(wrapper);
        // total
        AnaUser anaUser = anaUserMapper.selectOne(new QueryWrapper<AnaUser>().eq("date",data));
        // anaurl
        AnaKeyWord anaKeyWord = anaKeyWordMapper.selectOne(new QueryWrapper<AnaKeyWord>().eq("date",data));
        List<KeyValue> rankList = anaTextRankMapper.selectListByDay(data);
        Map<String,Object> map = new HashMap<>();
        map.put("word_top",urlTopList);
        map.put("word_avg",anaKeyWord.getWordAvg());
        map.put("total",anaUser.getTotal());
        map.put("word_count",anaKeyWord.getWordCount());
        map.put("avg_length",anaKeyWord.getAvgLength());
        map.put("key_arr",rankList);
        return map;
    }


    public String LdaService(String path){
        System.out.println(path);
        if (path.equals("default")){
            return "lda/20060801";
        }
        else {
            String LdaPath = path.replace("-", "");
            File file = new File("src/main/resources/templates/lda");
            String[] listFiles = file.list();
            List<String> files = Arrays.asList(listFiles);
            boolean check = files.contains(LdaPath + ".html");
            if (check) {
                return "lda/" + LdaPath;
            }
            return "lda/error";

        }
    }




}
