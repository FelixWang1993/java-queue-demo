package com.huazai.activemq.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pyh
 * @date 2021/1/17 14:57
 */
@SpringBootApplication
// 开启jms服务
@EnableJms
// 开启定时任务功能
@EnableScheduling
public class BootTopicProviderMain {
    public static void main(String[] args) {
        SpringApplication.run(BootTopicProviderMain.class, args);
    }
}
