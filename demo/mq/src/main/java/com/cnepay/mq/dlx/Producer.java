package com.cnepay.mq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        String exchange = "test_dlx_exchange";
        String routingKey = "dlx.save";


        String msg = "Hello RabbitMQ dlx Message";


        //5.发送一个消息
        for(int i=0;i<1;i++){
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .build();
            channel.basicPublish(exchange,routingKey,true,properties,msg.getBytes());
        }



    }
}
