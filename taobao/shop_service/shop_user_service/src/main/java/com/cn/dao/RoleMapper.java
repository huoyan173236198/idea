package com.cn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    //根据用户ID查询角色
    List<Role> queryRoleByUid(Integer uid);

}
