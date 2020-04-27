package com.cn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.GoodsType;

import java.util.List;

public interface GoodTypeMapper extends BaseMapper<GoodsType> {

    List<GoodsType> queryTypeByGid(Integer gid);

    List<GoodsType> queryAllById(Integer id);

    List<GoodsType> queryTypeAll();
}
