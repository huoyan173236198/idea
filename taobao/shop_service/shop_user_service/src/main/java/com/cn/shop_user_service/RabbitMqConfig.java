package com.cn.shop_user_service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
 */
@Configuration
public class RabbitMqConfig {
    /**创建队列1*/
    @Bean(name = "squeue")
    public Queue getQueue1(){
        return new Queue("search_queue");
    }
    /**创建队列2*/
    @Bean(name = "iqueue")
    public Queue getQueue2(){
        return new Queue("item_queue");
    }
    /**创建Fanout类型交换机*/
    @Bean(name = "gexchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("goods_exchange");
    }

    /**
     * 进行交换机和队列的绑定
     * 绑定的queue和exchange的名字会去上面的bean中的name中找，找不到会报错
     * 也可以直接用方法名绑定
     */
    @Bean
    public Binding getBinding1(Queue iqueue, FanoutExchange gexchange) {
        Binding binding = BindingBuilder.bind(iqueue).to(gexchange);
        return binding;
    }
    @Bean
    public Binding getBinding2(Queue squeue,FanoutExchange gexchange){
        return BindingBuilder.bind(squeue).to(gexchange);
    }
}
