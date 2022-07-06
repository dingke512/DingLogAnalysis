package com.dingke.myapp.common;
import org.springframework.stereotype.Component;


@Component
public class RuidGet {

    public static int RandomInt(){
        int a = 100;
        int b = 999;
        int num=a+(int)(Math.random()*(b-a+1));
        return num;
    }

    public String Longuid(){
        long time = System.currentTimeMillis();
        long temp = (long) Math.floor(time/100000);
        long end3 = time - (temp*100000);
        long temp2 = (long) Math.floor(temp/1000)*1000*100000;
        int num = RandomInt();
        String ruid = String.valueOf( temp2+num*100000+end3);
        return ruid;
    }

}
