package com.dingke.myapp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dingke.myapp.common.Hadoop;
import com.dingke.myapp.common.Message;
import com.dingke.myapp.dto.PasswordDto;
import com.dingke.myapp.dto.UpdateInfo;
import com.dingke.myapp.mapper.UserInfoMapper;
import com.dingke.myapp.mapper.UserMapper;
import com.dingke.myapp.pojo.User;
import com.dingke.myapp.pojo.UserInfo;
import com.dingke.myapp.vo.UserInfoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Hadoop hadoop;

    @Autowired
    private UserInfoMapper userInfoMapper;

    public Message loginService(String userid, String password, HttpServletRequest request){
        if (StringUtils.isNotBlank(userid)&&StringUtils.isNotBlank(password)) {
            User verify = userMapper.selectOne(new QueryWrapper<User>().eq("userid", userid).eq("password", password));

            if (ObjectUtils.isNotEmpty(verify)) {
                request.getSession().setAttribute("userid", userid);
                return Message.Success(2000,"ok", null);
            }
            return Message.Error(5001,"该用户不存在");
        }
        return Message.Error(5002,"用户名或密码为空");

    }


    public String checkService(String userid, Model model){
        if(StringUtils.isNotBlank(userid)){
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("userid",userid));
            String name = userInfo.getUsername();
            String img = userInfo.getImage();
            model.addAttribute("username",name);
            model.addAttribute("image","/images/"+img);
            model.addAttribute("hadoop","http://"+hadoop.getHost()+":"+hadoop.getPort());
            return "home";
        }
        return "login";
    }



    public UserInfoVo GetMyInfo(String userid){
        UserInfoVo userInfoVo = userMapper.getUserInfo(userid);
        userInfoVo.setPassword("");
        return userInfoVo;
    }



    @Transactional
    public Message updatePwd(PasswordDto passwordDto, String userid){
        String phone = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("userid",userid)).getPhone();
        String oldpwd = userMapper.querypwd(userid);
        if (!passwordDto.getPwd().equals(oldpwd)){
            return Message.Error(0,"旧密码错误");
        }

        else if (passwordDto.getPwd().equals(passwordDto.getNpwd())){
            return Message.Error(0,"旧密码与新密码一致");
        }

       else if (! passwordDto.getPhone().equals(phone)){
            return Message.Error(0,"手机号错误");
        }

        int res = userMapper.update(null,new UpdateWrapper<User>().eq("userid",userid).set("password", passwordDto.getNpwd()));
        if (res ==1){
            return Message.Success(1,"修改成功",null);
        }
        return Message.Error(0,"Mapper异常错误");
    }


    public void updateInfo(UpdateInfo updateInfo){
        String userid = updateInfo.getUserid();
        userInfoMapper.update(null,new UpdateWrapper<UserInfo>().eq("userid",userid)
                .set("age",updateInfo.getAge()).set("gender",updateInfo.getGender())
                .set("phone",updateInfo.getPhone()).set("username",updateInfo.getUsername()));


    }
    public void updateInfo(UpdateInfo updateInfo, String picName){
        String userid = updateInfo.getUserid();
        String img = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().select("image").eq("userid",userid)).getImage();
        System.out.println(img);
        try {
            File myImg = new File(img);
            myImg.delete();
        }
        catch (Exception exception){
            System.out.println(exception);
        }

        userInfoMapper.update(null,new UpdateWrapper<UserInfo>().eq("userid",userid)
                .set("age",updateInfo.getAge()).set("gender",updateInfo.getGender())
                .set("phone",updateInfo.getPhone()).set("username",updateInfo.getUsername()).set("image",picName));
    }

}
