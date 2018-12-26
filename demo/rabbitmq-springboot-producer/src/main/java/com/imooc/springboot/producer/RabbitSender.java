package com.imooc.springboot.producer;


import com.imooc.springboot.entity.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    //private AmqpTemplate amqpTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData:" + correlationData);
            System.out.println("ack : " + ack);
            if (!ack) {
                System.out.println("异常处理。。。");
            } else {
                //消息投递成功之后的业务处理
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };

    public void send(Object message, Map<String, Object> properties) {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("123456789");
        /*CorrelationData cd = new CorrelationData();
        cd.setId("123456"); */  //id + 时间戳 全局唯一
        //rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg, correlationData);
        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", "name:\"zhangsan\",psw:\"123456\"");
    }

    //发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Order order) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("0987654321");
        rabbitTemplate.convertAndSend("exchange-2", "springboot.def", order, correlationData);
        //rabbitTemplate.convertAndSend("exchange-2", "springboot.def", MessageBuilder.withPayload(order).build());

    }


}
