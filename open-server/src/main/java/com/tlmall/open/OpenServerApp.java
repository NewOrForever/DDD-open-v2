package com.tlmall.open;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author roy
 * @desc
 */
@SpringBootApplication
@MapperScan("com.tlmall.open.domain.*.infrastructure")
public class OpenServerApp {
    public static void main(String[] args) {
        SpringApplication.run(OpenServerApp.class,args);
    }
}
