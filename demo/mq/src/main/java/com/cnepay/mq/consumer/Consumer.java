package com.cnepay.mq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author zhangyq
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        //1.创建ConnctionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.105");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2.获取connnetion
        Connection connection = connectionFactory.newConnection();
        //3.创建channel
        Channel channel = connection.createChannel();


        String exchangeName = "test_consumer_exchange";
        String routingKey = "springboot.#";
        String queueName = "test_consumer_queue";

        //4.声明交换机 队列，然后进行绑定
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        //5.创建消费者

        channel.basicConsume(queueName,true,new MyConsumer(channel));


    }
}
