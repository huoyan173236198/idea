package com.cn.serviceimpl;

import com.cn.dao.GoodTypeMapper;
import com.cn.entity.GoodsType;
import com.cn.service.IGoodTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/23 14:16
 */
public class GoodTypeServiceImpl implements IGoodTypeService {

    public static final Logger log = LoggerFactory.getLogger(GoodTypeServiceImpl.class);

    @Autowired
    private GoodTypeMapper goodTypeMapper;

    @Override
    public List<GoodsType> goodsTypeList() {
        log.info("调用商品类型列表服务");
        return goodTypeMapper.queryTypeAll();
    }

    @Override
    public int goodsTypeInsert(GoodsType goodsType) {
        log.info("调用商品类型添加服务");
        return goodTypeMapper.insert(goodsType);
    }

    @Override
    public int goodsTypeDelete(GoodsType goodsType) {
        log.info("调用商品类型删除服务");
        return goodTypeMapper.deleteById(goodsType.getId());
    }

    @Override
    public List<GoodsType> queryAllById(Integer id) {
        return null;
    }


    @Override
    public int goodTypeUpdate(GoodsType goodsType) {
        log.info("调用商品类型修改服务");
        return goodTypeMapper.updateById(goodsType);
    }

    @Override
    public List<GoodsType> listAjax(Integer gid) {
        log.info("调用根据tid查询商品类型服务");
        return goodTypeMapper.queryTypeByGid(gid);
    }
}
