package com.dingke.myapp.controller;


import com.dingke.myapp.common.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resources")
public class ResourcesController {
    @GetMapping("/local")
    public String Resource(){
        return "resource";
    }

    @ResponseBody
    @GetMapping("/data")
    public Object GetResource(){

        return null;
    }

    @ResponseBody
    @GetMapping("/resources/data")
    public Message GetResources(){
        return null;
    }
}
