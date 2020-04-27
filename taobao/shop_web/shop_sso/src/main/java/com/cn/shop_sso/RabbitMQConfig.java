package com.cn.shop_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/15 20:00
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue getQueue(){
        return new Queue("email_queue");
    }
    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("email_exchange");
    }
    /**
     * 将方法名做为绑定的参数就可以不用使用bean中的name了
     */
    @Bean
    public Binding getBinding(Queue getQueue,FanoutExchange getFanoutExchange){
        return BindingBuilder.bind(getQueue).to(getFanoutExchange);
    }
}
