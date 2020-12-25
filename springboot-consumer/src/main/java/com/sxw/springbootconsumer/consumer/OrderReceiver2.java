package com.sxw.springbootconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.sxw.entity.Order;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderReceiver2 {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "order-queue2", durable = "true"), exchange = @Exchange(name = "order-exchange", type = "topic"), key = "order.2.*")})
    public void receive(@Payload Order order, @Headers Map<String, Object> headers,
                        Channel channel) throws IOException {


        //消费者操作
        System.out.println("order-queue2 ---------收到消息，开始消费---------");
        System.out.println("订单ID：" + order.getId());

        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("order-queue2 " + channel.toString());
        System.out.println("order-queue" + channel.getDefaultConsumer().toString());
    }
}
