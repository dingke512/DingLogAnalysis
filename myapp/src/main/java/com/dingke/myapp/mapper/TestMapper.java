package com.dingke.myapp.mapper;


import com.dingke.myapp.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {

    List<User> getUserInfo();
}
