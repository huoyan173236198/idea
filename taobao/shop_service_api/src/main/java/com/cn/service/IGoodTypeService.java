package com.cn.service;

import com.cn.entity.GoodsType;

import java.util.List;

public interface IGoodTypeService {

    //获取商品类型列表
    List<GoodsType> goodsTypeList();

    //添加商品类型
    int goodsTypeInsert(GoodsType goodsType);

    //删除商品类型
    int goodsTypeDelete(GoodsType goodsType);

    //修改类型前调用查询该类型状态
    List<GoodsType> queryAllById(Integer id);

    //修改商品类型
    int goodTypeUpdate(GoodsType goodsType);

    //根据tid获得类型列表状态
    List<GoodsType> listAjax(Integer gid);

}
