/*
package com.imooc.springboot;

import com.imooc.springboot.entity.Order;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.imooc.springboot.producer.RabbitSender;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringbootProducerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RabbitSender rabbitSender;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");

    @Test
    public void testSender1() {
        Map<String, Object> proterties = new HashMap<>();
        proterties.put("number", "12345");
        proterties.put("send_time", dateFormat.format(new Date()));
        rabbitSender.send("hello rabbitmq", proterties);
    }

    @Test
    public void testSender2() throws Exception {
        Order order = new Order("001", "第一个订单");
        rabbitSender.sendOrder(order);
    }


}

*/
