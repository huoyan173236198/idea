package com.cn.listener;

import com.cn.entity.Goods;
import com.cn.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/10 10:56
 */
@Component
public class RabbitMqListener {
    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "search_queue")
    public void msgHander(Goods goods){
        searchService.addGoods(goods);
    }
}
