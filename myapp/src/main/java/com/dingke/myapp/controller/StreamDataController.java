package com.dingke.myapp.controller;

import com.dingke.myapp.common.Message;
import com.dingke.myapp.service.StreamDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class StreamDataController {
    @Autowired
    private StreamDataService streamDataService;

    @GetMapping("/streaming")
    public String stream(){
        return "stream";
    }


    @ResponseBody
    @GetMapping("/streaming/data")
    public Map<String,Object> SteamData(){

        return streamDataService.StreamResult();
    }


}
