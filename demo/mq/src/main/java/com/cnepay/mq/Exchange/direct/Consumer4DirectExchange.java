package com.cnepay.mq.Exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangyq
 */
public class Consumer4DirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.1.105");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过Channection创建一个channel
        Channel channel = connection.createChannel();

        //4.声明（创建）一个队列
        String exchangeName = "test_direct_echange";
        String exchangeType = "direct";
        String routingKey = "test.direct";
        String queueName = "test_direct_queue";
        //声明交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //声明队列
        channel.queueDeclare(queueName,false,false,false,null);
        //建立一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);


        //5.创建消费者   //durable 是否持久化消息
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);


        channel.basicConsume(queueName,true,queueingConsumer);

        while(true){
            //7.获取消息 如果没有消息，这一步会一直阻塞
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端："+msg);
            //Envelope envelope = delivery.getEnvelope();


        }



    }
}
