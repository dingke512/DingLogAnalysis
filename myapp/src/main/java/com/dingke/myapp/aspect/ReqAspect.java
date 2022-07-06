package com.dingke.myapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Component
//@Aspect

//@Aspect、@Component、@Pointcut、@Before、@AfterReturning
public class ReqAspect {
    //切入点，一个是使用 execution()
    //另一个是使用 annotation()
    @Pointcut("execution( * com.dingke.myapp.controller.UsersController.*(..))")
    private void pointcut01(){
    }
    //
    @Before("pointcut01()")
    public void advice01(){
        //操作
        System.out.println("this is advice01");
    }


    @Pointcut("execution( * com.dingke.myapp.service.UserService.loginService(..))")
    private void pointcut02(){
    }

    @Around("pointcut02()")
    //不适用Obj返回相当于修改了原方法的return，原方法没有return
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");
        Object proceed = jp.proceed();  //执行方法,拿返回值
        System.out.println(proceed.toString());
        Signature signature = jp.getSignature();  //获得签名
        System.out.println(signature);
        System.out.println("环绕后");
        return proceed;
    }

}
