package com.cnepay.mq.Exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangyq
 */
public class Procuder4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.1.105");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过Channection创建一个channel
        Channel channel = connection.createChannel();

        //4.声明
        String exchangeName = "test_direct_echange";
        String routingKey = "test.direct";
        //5.发送

        String msg = "hello RabbitMQ 4 Direct Exchange";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

    }
}
