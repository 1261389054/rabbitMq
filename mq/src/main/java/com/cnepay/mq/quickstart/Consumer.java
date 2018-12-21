package com.cnepay.mq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangyq
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3.通过Channection创建一个channel
        Channel channel = connection.createChannel();


    }
}
