package com.sxw.springbootconsumer.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * @author zhangwei
 * @date 2020/12/20 21:48
 **/
@Component
public class DeadLetterMessageReceiver {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "orderDead-queue", durable = "true"), exchange = @Exchange(name = "orderDead-exchange"), key = "dead")})
    public void receiveA(Message message, Channel channel) throws IOException {
        System.out.println("收到死信消息A：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
