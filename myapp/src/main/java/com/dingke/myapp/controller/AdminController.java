package com.dingke.myapp.controller;


import com.dingke.myapp.dto.UserInfoDto;
import com.dingke.myapp.dto.UserUpdateDto;
import com.dingke.myapp.service.AdminService;
import com.dingke.myapp.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/user/manager")
    public String userManager (Model model,HttpServletRequest request){
        String userid = (String) request.getSession().getAttribute("userid");

        return adminService.userInfoService(userid,model);
    }

    @PostMapping("/user/delete")
    public String userDel(@RequestParam("userid") String userid){
        System.out.println(userid);
        adminService.userDel(userid);
        return "redirect:/Admin/user/manager";
    }

    @PostMapping("/user/add")
    public String userAdd(@ModelAttribute UserInfoDto userInfoDto ,Model model){
        Map<String,Object> result = adminService.userAdd(userInfoDto);
        model.addAttribute("state",result.get("state"));
        model.addAttribute("message",result.get("message"));
        model.addAttribute("userid",result.get("userid"));
        return "register";
    }

    @PostMapping("/user/update")
    public String userUpdate(@ModelAttribute UserUpdateDto userUpdateDto ){
        System.out.println(userUpdateDto);
        adminService.userUpdate(userUpdateDto);
        return "redirect:/Admin/user/manager";
    }

    @GetMapping("/user/queryby")
    public String userQueryBy(@RequestParam String userid,@RequestParam String phone,
                              @RequestParam String priority,Model model,HttpServletRequest request){
        System.out.println(userid+"  "+ phone+" "+priority);
        String id = (String) request.getSession().getAttribute("userid");
        int p = adminService.GetPriority(id);
        if (StringUtils.isBlank(userid) && StringUtils.isBlank(phone) && StringUtils.isBlank(priority)) {
            return "redirect:/Admin/user/manager";
        }
        else if(p==1){
            List<UserInfoVo> userInfoList = adminService.queryBy(phone,userid,priority);
            model.addAttribute("user_list",userInfoList);
            return "usermanager";
        }
        return "priorityerror";
    }
}
