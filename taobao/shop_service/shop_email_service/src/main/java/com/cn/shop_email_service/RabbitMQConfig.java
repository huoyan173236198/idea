package com.cn.shop_email_service;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/20 23:12
 */
@Configuration
public class RabbitMQConfig {
    public Queue getQueue(){
        return new Queue("email_queue");
    }
}
