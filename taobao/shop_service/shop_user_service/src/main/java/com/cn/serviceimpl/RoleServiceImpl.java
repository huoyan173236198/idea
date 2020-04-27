package com.cn.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.service.IRoleService;
import com.cn.dao.RoleMapper;
import com.cn.dao.RolePowerMapper;
import com.cn.entity.Role;
import com.cn.entity.RolePowerTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/11 11:54
 */
//角色服务实现类
@Service
public class RoleServiceImpl implements IRoleService {
    public static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePowerMapper rolePowerMapper;
    private RoleServiceImpl(){
        log.info("调用了角色服务");
    }
    //获取角色列表
    @Override
    public List<Role> roleList() {
        log.info("获取角色列表");
        return roleMapper.selectList(null);
    }
    //添加角色
    @Override
    public int insertRole(Role role) {
        log.info("添加角色");
        return roleMapper.insert(role);
    }
    //修改角色
    @Override
    public int updateRole(Role role) {
        log.info("修改角色");
        return roleMapper.updateById(role);
    }
    //删除角色
    @Override
    public int deleteRole(Role role) {
        log.info("删除角色");
        return roleMapper.deleteById(role.getId());
    }
    //根据用户ID查看是否拥有角色
    @Override
    public List<Role> listajax(Integer uid) {
        log.info("根据用户ID查看是否拥有角色");
        List<Role> roles = roleMapper.queryRoleByUid(uid);
        for (Role role : roles) {
            log.info(role.toString());
        }
        return roles;
    }

    //修改角色权限
    @Transactional
    @Override
    public int updateRolePower(Integer rid,Integer[] powerId) {
        log.info("修改角色权限---角色ID："+rid+",更新的权限ID："+ Arrays.toString(powerId));
        //删除该角色绑定的权限
        QueryWrapper<RolePowerTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        rolePowerMapper.delete(queryWrapper);

        //重新添加角色权限关系
        for (Integer pwid : powerId) {
            RolePowerTable rolePowerTable = new RolePowerTable(rid,pwid);
            rolePowerMapper.insert(rolePowerTable);
        }
        return 0;
    }
}
