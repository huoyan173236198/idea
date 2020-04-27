package com.cn.service;

import com.cn.entity.Role;

import java.util.List;

//角色接口
public interface IRoleService {
    //角色集合
    List<Role> roleList();

    //添加角色
    int insertRole(Role role);

    //修改角色
    int updateRole(Role role);

    //删除角色
    int deleteRole(Role role);

    //根据用户ID查询是否拥有角色
    List<Role> listajax(Integer uid);

    //修改角色所拥有的权限
    int updateRolePower(Integer rid,Integer[] powerId);

}
