package com.dingke.myapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingke.myapp.vo.UserInfoVo;
import com.dingke.myapp.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) ")
    List<UserInfoVo> userInfoList();


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) WHERE userid = #{userid} ")
    UserInfoVo getUserInfo(String userid);


    @Select("SELECT password from db_user WHERE userid = #{userid} ")
    String querypwd(String userid);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where phone=#{phone}")
    List<UserInfoVo> queryByPhone(String phone);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where userid=#{userid}")
    List<UserInfoVo> queryById(String userid);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where priority=#{priority}")
    List<UserInfoVo> queryByPri(int priority);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where userid=#{userid} and phone=#{phone}")
    List<UserInfoVo> queryByIdPhone(String userid,String phone);

    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where phone=#{phone} and priority=#{priority}")
    List<UserInfoVo> queryByphonPri(String phone,int priority);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where userid=#{userid} and priority=#{priority}")
    List<UserInfoVo> queryByIdPri(String userid,int priority);


    @Select("SELECT userid,username,gender,age,phone,image,password,priority FROM db_user a LEFT JOIN db_user_info b USING(userid) " +
            "where userid=#{userid} and priority=#{priority} and phone=#{phone}")
    List<UserInfoVo> queryByAll(String userid,int priority,String phone);

}
