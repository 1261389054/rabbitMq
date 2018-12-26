package com.cnepay.mq.returnlistener;

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

        String exchange = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Message";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("---------handle  return----------");
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
            }
        });
        //5.发送一个消息
        //channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());


    }
}
