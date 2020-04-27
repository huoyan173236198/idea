package com.cn.service;

import com.cn.entity.Goods;

import java.util.List;

public interface IGoodsService {

    //获取商品列表
    List<Goods> goodsList();

    //添加商品
    int goodsInsert(Goods goods);

    //删除商品
    int goodsDelete(Goods goods);

    //修改商品
    int goodsUpdate(Goods goods);

}
