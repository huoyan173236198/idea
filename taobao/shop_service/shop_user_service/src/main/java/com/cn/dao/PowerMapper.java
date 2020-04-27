package com.cn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.Power;

import java.util.List;

public interface PowerMapper extends BaseMapper<Power> {
    List<Power> queryPowerByRid(Integer rid);

    List<Power> queryPowerAll();
}
