package com.cn.shop_search_service;

import com.cn.entity.Goods;
import com.cn.service.ISearchService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopSearchServiceApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Test
    void contextLoads() {
        Goods goods = new Goods();

        goods.setId(1);
        goods.setGname("苹果电脑");
        goods.setGinfo("好用的笔记本");
        goods.setGimage("123456");
        goods.setGprice(new BigDecimal("2699.36"));
        goods.setGsave(666);
        searchService.addGoods(goods);
    }

}
