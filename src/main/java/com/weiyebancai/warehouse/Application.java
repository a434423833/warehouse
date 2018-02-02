package com.weiyebancai.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author CaoHao
 * @Description:springboot启动
 * @2017年11月7日 下午2:14:25
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.weiyebancai.warehouse.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

}