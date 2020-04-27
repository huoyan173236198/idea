package com.cn.service;

import com.cn.entity.BackUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IBackUserService extends UserDetailsService {
    //查询后台用户列表
    List<BackUser> quarryAll();

    //登录,使用权限控制后该方法在其实现类的UserDetails方法中去实现
    //BackUser login(String username, String password);

    //添加用户
    int insertUser(BackUser backUser);

    //修改用户
    int updateUser(BackUser backUser);

    //删除用户
    int deleteUser(BackUser backUser);

    //修改用户所拥有的角色(可能添加多个角色)
    int updateUserRole(Integer uid, Integer[] rid);
}
