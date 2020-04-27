package com.cn.shop_search_service;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/9 21:18
 */

/**
 * rabbitmq配置类
 * 防止启动的先后顺序导致找不到该队列所以这边也创建一下队列防止报错
 */
@Configuration
public class RabbitMqConfig {
    /**创建队列*/
    @Bean
    public Queue getQueue(){
        return new Queue("search_queue");
    }
    
}
