package com.javaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program: project_web
 * @description: 启动类
 * @author: YuKai Fan
 * @create: 2019-04-24 11:53
 **/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * id生成器
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }
}