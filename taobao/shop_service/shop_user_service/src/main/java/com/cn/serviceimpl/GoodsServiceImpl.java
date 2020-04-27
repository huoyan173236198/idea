package com.cn.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.dao.GoodsMapper;
import com.cn.entity.Goods;
import com.cn.service.IGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/21 17:07
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    public static final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Goods> goodsList() {
        log.info("调用商品列表服务");
        return goodsMapper.selectList(null);
    }

    @Override
    public int goodsInsert(Goods goods) {
        log.info("调用商品添加服务");
        //要先执行添加的业务再执行放入队列的业务，此时已经主键回填，不然没有主键值
        int insert = goodsMapper.insert(goods);
        rabbitTemplate.convertAndSend("goods_exchange","",goods);
        return insert;
    }

    @Override
    public int goodsDelete(Goods goods) {
        log.info("调用商品删除服务");
        return goodsMapper.deleteById(goods.getId());
    }

    @Override
    public int goodsUpdate(Goods goods) {
        log.info("调用商品修改服务");
        return goodsMapper.updateById(goods);
    }
}
