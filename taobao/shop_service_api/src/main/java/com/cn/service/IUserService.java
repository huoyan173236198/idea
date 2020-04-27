package com.cn.service;

import com.cn.entity.User;

public interface IUserService {
    /**
     * 用户注册
     */
    int register(User user);

    /**
     * 根据用户名查询用户
     */
    User queryByUsername(String username);

    /**
     *根据用户名修改密码
     */
    User updatePassword(String username, String password);

    /**
     * 登录时根据用户对象查询用户
     */
    User login(User user);
}
