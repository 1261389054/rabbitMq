package com.cnepay.mq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangyq
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建ConnctionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.105");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2.获取connnetion
        Connection connection = connectionFactory.newConnection();
        //3.创建channel
        Channel channel = connection.createChannel();
        //4.指定消息投递模式，消息确认模式
        channel.confirmSelect();

        String exchange = "test_consumer_exchange";
        String routingKey = "springboot.save";


        String msg = "Hello RabbitMQ springboot Message";


        //5.发送一个消息
        for(int i=0;i<5;i++){
            channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        }



    }
}
