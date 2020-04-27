package com.cn.service;

import com.cn.entity.Goods;

import java.util.List;

public interface ISearchService {
    //根据关键字搜索商品
    List<Goods> searchByKey(String keyword);
    //添加商品时不仅要添加到数据库中，也要在solr索引库中添加一份
    int addGoods(Goods goods);
}
