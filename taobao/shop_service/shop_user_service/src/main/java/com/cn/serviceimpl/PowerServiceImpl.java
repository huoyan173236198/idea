package com.cn.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.service.IPowerService;
import com.cn.dao.PowerMapper;
import com.cn.entity.Power;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/13 19:27
 */
@Service
public class PowerServiceImpl implements IPowerService {

    public static final Logger log = LoggerFactory.getLogger(PowerServiceImpl.class);

    @Autowired
    private PowerMapper powerMapper;

    //添加权限
    @Override
    public int insertPower(Power power) {
        log.info("添加权限");
        log.info("添加的权限为：" + power.toString());
        return powerMapper.insert(power);
    }

    //权限列表
    @Override
    public List<Power> powerList() {
        log.info("权限列表");
        List<Power> powers = powerMapper.queryPowerAll();
        for (Power power : powers) {
            log.info("查到的权限为：" + power.toString());
        }
        return powers;
    }

    //权限修改
    @Override
    public int updatePower(Power power) {
        log.info("修改权限");
        log.info("修改的权限为：" + power.toString());
        powerMapper.updateById(power);
        return 0;
    }
    //删除权限
    @Override
    public int deletePower(Power power) {
        log.info("删除权限");
        log.info("删除的权限为：" + power.toString());
        powerMapper.deleteById(power.getId());
        return 0;
    }

    //根据角色ID获取当前所拥有的权限
    @Override
    public List<Power> listajax(Integer rid) {
        log.info("根据角色ID获取所拥有权限");
        List<Power> powers = powerMapper.queryPowerByRid(rid);
        for (Power power : powers) {
            log.info("是否拥有权限"+power.toString());
        }
        return powers;
    }
}
