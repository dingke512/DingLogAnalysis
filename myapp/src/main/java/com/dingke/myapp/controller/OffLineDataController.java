package com.dingke.myapp.controller;

import com.dingke.myapp.mapper.AnaUserMapper;
import com.dingke.myapp.service.AnaDataService;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/offline")
public class OffLineDataController {

    @Autowired
    private AnaDataService dataService;



    @GetMapping("/ana_trend")
    public String ResultTrend(@RequestParam(name = "start" ,defaultValue = "2006-08-01") String start,
                              @RequestParam(name = "end",defaultValue = "2006-08-30") String end, Model model){
        model.addAttribute("start",start.replace("-",""));
        model.addAttribute("end",end.replace("-",""));
        return "trend";
    }
    @ResponseBody
    @GetMapping("/ana_trend/data")
    public Map<String,Object> ResultTrendData(@RequestParam(name = "start" ,defaultValue = "2006-08-01") String start,
                              @RequestParam(name = "end",defaultValue = "2006-08-30") String end, Model model){

        return dataService.AnaTrendService(start,end);
    }



    @GetMapping("/ana_user")
    public String ResultUser(@RequestParam(value = "date",defaultValue = "2006-08-01") String date,
                             @RequestParam(value = "n",defaultValue = "20") Integer n,
                             Model model){
        log.debug(date,n);
        model.addAttribute("date",date.replace("-",""));
        model.addAttribute("n",n);
        return "user_ana";
    }

    @ResponseBody
    @GetMapping("/ana_user/data")
    public Map<String, Object> ResultUserDate(@RequestParam(value = "date",defaultValue = "20060801") String date,
                                              @RequestParam(value = "n",defaultValue = "20") Integer n){
        return dataService.AnaUserService(date,n);
    }


    @GetMapping("/ana_url")
    public String ResultUrl(@RequestParam(value = "date",defaultValue = "2006-08-01") String date,
                            @RequestParam(value = "n",defaultValue = "20") Integer n,
                            Model model){
        log.debug(date,n);
        model.addAttribute("date",date.replace("-",""));
        model.addAttribute("n",n);
        return "url_ana";
    }
    @ResponseBody
    @GetMapping("/ana_url/data")
    public Map<String, Object> ResultUrlDate(@RequestParam(value = "date",defaultValue = "20060801") String date,
                                              @RequestParam(value = "n",defaultValue = "20") Integer n){
        return dataService.AnaUrlService(date,n);
    }


    @GetMapping("/ana_keywords")
    public String ResultWord(@RequestParam(value = "date",defaultValue = "2006-08-01") String date,
                             @RequestParam(value = "n",defaultValue = "20") Integer n,
                             Model model){
        log.debug(date,n);
        model.addAttribute("date",date.replace("-",""));
        model.addAttribute("n",n);
        return "keywords";
    }


    @ResponseBody
    @GetMapping("/ana_keywords/data")
    public Map<String, Object> ResultWordDate(@RequestParam(value = "date",defaultValue = "20060801") String date,
                                             @RequestParam(value = "n",defaultValue = "20") Integer n){
        return dataService.AnaKeyWordsService(date,n);
    }


    @GetMapping("/lda")
    public String lda(){
        return "lda_visual";
    }


    @GetMapping("/ana_lda/{path}")
    public String LdaPage(@PathVariable(value = "path",required = false)String path) {
        return dataService.LdaService(path);
    }


}
