package com.cnepay.mq.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        String exchange = "test_ack_exchange";
        String routingKey = "ack.save";





        //5.发送一个消息
        for(int i=0;i<5;i++){
            Map<String,Object> headers = new HashMap<>();
            headers.put("num",i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000") //消息过期时间
                    .headers(headers)
                    .build();
            String msg = "Hello RabbitMQ ack Message" + ">>>"+i;
            channel.basicPublish(exchange,routingKey,true,properties,msg.getBytes());
        }



    }
}
