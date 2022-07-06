package com.dingke.myapp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private int code;
    private String message;
    private Object data;


    public static Message Success(int code,String message, Object object){
        return new Message(code,message,object);
    }

    public static Message Error(int code,String message){
        return new Message(code,message,null);
    }


    public static Message Error(int code,String message, Object object){
        return new Message(code,message,object);
    }

}
