package com.wkrj.core.component.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 消息监听器（消费者）
 * @author ziro
 * @date 2019/7/18 16:32
 */
@Component
public class WkrjtestListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(WkrjtestListener.class);

    /**
     * 监听消息，接收并处理
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        System.out.println("customer");
        System.out.println(message);
        logger.info("receive message:{}",message);
    }

    /*发送消息（生产者）------此处只是示范用法，具体使用请写到自己的业务代码中。*/
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void send(){
        //发送消息 参数1：key,要与配置文件中一直  参数2：value
        amqpTemplate.convertAndSend("wkrjtestKey","wkrjTestValue");
    }

}
