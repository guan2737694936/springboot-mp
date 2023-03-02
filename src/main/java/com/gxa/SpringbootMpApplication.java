package com.gxa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.gxa.modules.sys.mapper"})
public class SpringbootMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMpApplication.class, args);
    }

}
