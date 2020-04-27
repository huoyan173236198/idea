package com.cn.service;

import com.cn.entity.Power;

import java.util.List;

public interface IPowerService {
    //添加权限
    int insertPower(Power power);

    //查询权限列表
    List<Power> powerList();

    //修改权限
    int updatePower(Power power);

    //删除权限
    int deletePower(Power power);
    //根据角色ID查询所拥有的权限
    List<Power> listajax(Integer rid);
}
