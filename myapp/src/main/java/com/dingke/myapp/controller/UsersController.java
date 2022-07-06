package com.dingke.myapp.controller;
import com.dingke.myapp.common.Message;
import com.dingke.myapp.dto.PasswordDto;
import com.dingke.myapp.dto.UpdateInfo;
import com.dingke.myapp.dto.UserInfoDto;
import com.dingke.myapp.dto.UserUpdateDto;
import com.dingke.myapp.service.UserService;
import com.dingke.myapp.utils.FileUtils;
import com.dingke.myapp.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
public class UsersController {
    @Autowired
    private UserService userservice;

    @GetMapping("/")
    public String index(){
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public Message login(@RequestBody Map<String,String> map, HttpServletRequest request){

        String userid = map.get("username");
        String password = map.get("password");
        return userservice.loginService(userid,password,request);

    }

    @GetMapping("/check")
    public String check(HttpServletRequest request,Model model){
        String userid = (String) request.getSession().getAttribute("userid");
        return userservice.checkService(userid,model);
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
//         del sesssion
        request.getSession().invalidate();
//        redirect
         response.sendRedirect("/");
    }

    @RequestMapping("/user/updatepwd")
    public String updatePwd(){
        return "updatepwd";
    }


    @ResponseBody
    @PostMapping("/user/ajax_pwd")
    public Message updatePwdSub(@ModelAttribute PasswordDto passwordDto,HttpServletRequest request){
        String userid = (String) request.getSession().getAttribute("userid");
        System.out.println(passwordDto);
        if (ObjectUtils.allNotNull(passwordDto)){
           return userservice.updatePwd(passwordDto,userid);
        }
        return Message.Error(0,"字段不能为空");

    }


    @GetMapping("/user/setting")
    public String userSetting(HttpServletRequest request,Model model){
        String userid = (String) request.getSession().getAttribute("userid");
        if(StringUtils.isNotBlank(userid)) {
            UserInfoVo userInfoVo = userservice.GetMyInfo(userid);
            model.addAttribute("user",userInfoVo);
            return "setting";
        }
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/user/update")
    public void userUpdate(@ModelAttribute UpdateInfo updateInfo,
                                @RequestParam MultipartFile pic,
                           HttpServletResponse response) throws IOException {

        System.out.println(updateInfo);
        System.out.println(pic.getOriginalFilename());
        System.out.println(pic.isEmpty());

        if (pic.isEmpty())
        {
            userservice.updateInfo(updateInfo);
        }
        else {
            String fileName = pic.getOriginalFilename();
            FileInputStream inputStream = (FileInputStream) pic.getInputStream();
            File f = new File("src/main/resources/media");
            System.out.println(f.getAbsolutePath());
            String res = FileUtils.upload(pic, f.getAbsolutePath(), fileName);


            System.out.println(res);
            userservice.updateInfo(updateInfo,res);
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("<h1>修改成功,刷新后生效</h1>");
        return ;

    }


}
