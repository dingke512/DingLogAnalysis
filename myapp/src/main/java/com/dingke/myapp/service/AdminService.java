package com.dingke.myapp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingke.myapp.common.RuidGet;
import com.dingke.myapp.dto.UserInfoDto;
import com.dingke.myapp.dto.UserUpdateDto;
import com.dingke.myapp.mapper.UserInfoMapper;
import com.dingke.myapp.mapper.UserMapper;
import com.dingke.myapp.pojo.User;
import com.dingke.myapp.pojo.UserInfo;
import com.dingke.myapp.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class AdminService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RuidGet ruidGet;

    public String userInfoService(String userid,Model model){
        int priority = GetPriority(userid);
        if (priority==1) {
            List<UserInfoVo> userInfoList = userMapper.userInfoList();
            model.addAttribute("user_list",userInfoList);

            return "usermanager";
        }
        return "priorityerror";
    }


    public int GetPriority(String userid){
       User user = userMapper.selectOne(new QueryWrapper<User>().eq("userid",userid));
       return user.getPriority();
    }

    @Transactional
    public Map<String,Object> userAdd(UserInfoDto userInfoDto){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        UserInfo userInfo = new UserInfo();
        String uid = ruidGet.Longuid();
        user.setUserid(uid).setPassword(userInfoDto.getPassword()).setPriority(userInfoDto.getPriority());
        userMapper.insert(user);
        int res = userInfoMapper.insert(userInfo.setUserid(uid).setAge(userInfoDto.getAge())
                .setUsername(userInfoDto.getUsername()).setGender(userInfoDto.getGender()).setImage("default.jpg")
                .setPhone(userInfoDto.getPhone()));
        if (res ==1){
            map.put("message","注册成功");
            map.put("state",1);
            map.put("userid",uid);
        }
       else {
            map.put("message","注册失败");
            map.put("state",0);
            map.put("userid","None");
        }
        return map;
    }

    @Transactional
    public void userDel(String userid){
        userMapper.delete(new QueryWrapper<User>().eq("userid",userid));
        int res = userInfoMapper.delete(new QueryWrapper<UserInfo>().eq("userid",userid));
        log.info(String.valueOf(res));
    }

    @Transactional
    public void userUpdate(UserUpdateDto userInfoDto){
        System.out.println(userInfoDto);
        User user = new User();
        UserInfo userInfo = new UserInfo();
        user.setUserid(userInfoDto.getUserid()).setPassword(userInfoDto.getPassword()).setPriority(userInfoDto.getPriority());
        userMapper.update(user,new QueryWrapper<User>().eq("userid",user.getUserid()));
        userInfo.setUserid(userInfoDto.getUserid()).setAge(userInfoDto.getAge())
                .setUsername(userInfoDto.getUsername()).setGender(userInfoDto.getGender()).setImage("default.jpg")
                .setPhone(userInfoDto.getPhone());
        System.out.println(userInfo);
        int res = userInfoMapper.update(userInfo,new QueryWrapper<UserInfo>().eq("userid",userInfo.getUserid()));
        System.out.println(res);

    }


    public List<UserInfoVo> queryBy(String phone,String userid, String priority){

        //1 0 0
        if (StringUtils.isNotBlank(phone) && StringUtils.isBlank(userid) && StringUtils.isBlank(priority)){
            return userMapper.queryByPhone(userid);
        }
        //0 1 0
        else if (StringUtils.isBlank(phone) && StringUtils.isNotBlank(userid) && StringUtils.isBlank(priority)){
            return userMapper.queryById(userid);
        }
        //0 0 1
        else if (StringUtils.isBlank(phone) && StringUtils.isBlank(userid) && StringUtils.isNotBlank(priority)){
            return userMapper.queryByPri(Integer.parseInt(priority));
        }
        // 1 1 0
        else if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(userid) && StringUtils.isBlank(priority)){
            return userMapper.queryByIdPhone(userid,phone);
        }
        // 1 0 1
        else if (StringUtils.isNotBlank(phone) && StringUtils.isBlank(userid) && StringUtils.isNotBlank(priority)){
            return userMapper.queryByphonPri(phone,Integer.parseInt(priority));
        }
        // 0 1 1
        else if (StringUtils.isBlank(phone) && StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(priority)){
            return userMapper.queryByIdPri(userid,Integer.parseInt(priority));
        }
        // 1 1 1
        else if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(priority)){
            return userMapper.queryByAll(userid,Integer.parseInt(priority),phone);
        }
        return null;

    }

}
