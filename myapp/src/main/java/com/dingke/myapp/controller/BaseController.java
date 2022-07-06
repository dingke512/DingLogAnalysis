package com.dingke.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BaseController {

    @GetMapping("/welcome")
    public String welocome(){
        return "welcome";
    }


    @GetMapping("/error/login")
    public String error(){
        return "sessionerror";
    }


}
