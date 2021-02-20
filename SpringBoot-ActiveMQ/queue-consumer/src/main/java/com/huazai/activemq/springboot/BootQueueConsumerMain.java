package com.huazai.activemq.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pyh
 * @date 2021/1/16 18:51
 */
@SpringBootApplication
// 开启jms服务
@EnableJms
public class BootQueueConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(BootQueueConsumerMain.class, args);
    }
}
