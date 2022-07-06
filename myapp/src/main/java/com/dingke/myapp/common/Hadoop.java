package com.dingke.myapp.common;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data

public class Hadoop {
    @Value("${hadoop.host}")
    private String host;
    @Value("${hadoop.port}")
    private String port;
}
