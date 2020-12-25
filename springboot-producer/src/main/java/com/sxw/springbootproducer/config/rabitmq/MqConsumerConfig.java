package com.sxw.springbootproducer.config.rabitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConsumerConfig {

    //@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 必须设置为 true，不然当 发送到交换器成功，但是没有匹配的队列，不会触发 ReturnCallback 回调
        // 而且 ReturnCallback 比 ConfirmCallback 先回调，意思就是 ReturnCallback 执行完了才会执行 ConfirmCallback
        rabbitTemplate.setMandatory(true);
        // 设置 ConfirmCallback 回调   yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 如果发送到交换器都没有成功（比如说删除了交换器），ack 返回值为 false
            // 如果发送到交换器成功，但是没有匹配的队列（比如说取消了绑定），ack 返回值为还是 true （这是一个坑，需要注意）
            if (ack) {
                String messageId = correlationData.getId();
                System.out.println("confirm:" + messageId);
            }
        });
        // 设置 ReturnCallback 回调   yml需要配置 publisher-returns: true
        // 如果发送到交换器成功，但是没有匹配的队列，就会触发这个回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText,
                                          exchange, routingKey) -> {
            String messageId = message.getMessageProperties().getMessageId();
            System.out.println("return:" + messageId);
        });
        return rabbitTemplate;
    }

}
