package com.huazai.activemq.broker;

import org.apache.activemq.broker.BrokerService;

/**
 * @author pyh
 * @date 2021/1/6 23:01
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        // 创建broker服务实例
        BrokerService brokerService = new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        // 绑定地址，此处不是再使用linux服务器上的ip地址
        brokerService.addConnector("tcp://127.0.0.1:61616");
        // 启动服务
        brokerService.start();
    }
}
