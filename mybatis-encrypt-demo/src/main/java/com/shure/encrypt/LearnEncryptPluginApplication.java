package com.shure.encrypt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.shure.encrypt.dao")
@SpringBootApplication
public class LearnEncryptPluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnEncryptPluginApplication.class, args);
    }

}
