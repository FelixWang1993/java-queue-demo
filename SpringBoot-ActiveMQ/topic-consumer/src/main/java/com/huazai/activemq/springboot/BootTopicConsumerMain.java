package com.huazai.activemq.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author pyh
 * @date 2021/1/17 14:56
 */

@SpringBootApplication
// 开启jms服务
@EnableJms
public class BootTopicConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(BootTopicConsumerMain.class, args);
    }
}
