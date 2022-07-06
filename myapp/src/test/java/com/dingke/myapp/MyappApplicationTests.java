package com.dingke.myapp;

import com.alibaba.fastjson.JSON;
import com.dingke.myapp.common.RuidGet;
import com.dingke.myapp.mapper.AnaUserMapper;
import com.dingke.myapp.mapper.TestMapper;
import com.dingke.myapp.mapper.UserMapper;
import com.dingke.myapp.pojo.User;
import com.dingke.myapp.service.AnaDataService;
import com.dingke.myapp.vo.UserInfoVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MyappApplicationTests {
    @Autowired
    private RedisTemplate<String,String>  redisTemplate; //操作key-value都是字符串，最常用

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnaDataService anaDataService;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private RuidGet ruidGet;

    @Test
    public void test(){
        String data = redisTemplate.opsForValue().get("word_top");
//        System.out.println(data);

        Map mapTypes = JSON.parseObject(data);
        System.out.println("这个是用JSON类的parseObject来解析JSON字符串!!!");
        for (Object obj : mapTypes.keySet()){
            System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
        }

        System.out.println(mapTypes.getClass().getTypeName());

    }


    @Test

    public void test2(){

//       List<UserInfoVo> userInfoList = userMapper.userInfoList();
//       userInfoList.forEach(System.out::println);
        Map<String,Object> map = anaDataService.AnaTrendService("20060801","20060810");
        System.out.println(map);
    }
    @Test
    public void t3(){
        List<User> users = testMapper.getUserInfo();
        System.out.println(users);

    }


}
