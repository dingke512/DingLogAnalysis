package com.dingke.myapp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo request url
        String url = request.getRequestURI();
        //todo get session
        HttpSession httpSession = request.getSession();
        String userid = (String) httpSession.getAttribute("userid");
        //todo check session
        if (StringUtils.isBlank(userid)) {
            log.error("handler:"+handler);
            //todo  redirect
            response.sendRedirect("/error/login");
            return false;
        }
        log.warn(request+"\t"+url);

        return true;

        }
}


