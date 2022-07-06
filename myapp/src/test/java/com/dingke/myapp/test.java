package com.dingke.myapp;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        String  res = jedis.get("data");
        System.out.println(res);

        HashMap hashMap = JSON.parseObject(res, HashMap.class);

        System.out.println(hashMap);

    }

}


