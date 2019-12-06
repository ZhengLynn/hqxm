package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  11:29
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.baizhi.dao")
public class CmfzApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class, args);
    }
}
