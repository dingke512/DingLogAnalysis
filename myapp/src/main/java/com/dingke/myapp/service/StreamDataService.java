package com.dingke.myapp.service;

import com.dingke.myapp.vo.StreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.util.*;

@Service
public class StreamDataService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Async("StreamData")
    public Map<String, Object> StreamResult() {

        Map <String,Object> map = new HashMap<>();
        map.put("user_count",redisTemplate.opsForValue().get("user_count"));
        map.put("page_count",redisTemplate.opsForValue().get("page_count"));
        map.put("word_count",redisTemplate.opsForValue().get("word_count"));
        map.put("flow_count",redisTemplate.opsForValue().get("flow_count"));
        map.put("page_total",redisTemplate.opsForValue().get("page_total"));
        map.put("user_total",redisTemplate.opsForValue().get("user_total"));
        //todo topk
        map.put("arr_user",processRedisJson("user_top"));
        map.put("arr_page",processRedisJson("page_top"));

        return map;
    }

    private List<Object> processRedisJson(String str){
        List<Object> ResList = new LinkedList<>();
        Map<String,Integer> ItemMap=JSON.parseObject(redisTemplate.opsForValue().get(str), HashMap.class);
        Set<String> keySet = ItemMap.keySet();
        if (keySet.size() != 0) {
            for (String key : keySet){
                StreamData s = new StreamData();
                s.setKey(key).setCount(ItemMap.get(key));
                ResList.add(s);
            }
        }
        return ResList;
    }
}
