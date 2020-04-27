package com.cn.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.dao.UserMapper;
import com.cn.entity.User;
import com.cn.pass.BcryptUtils;
import com.cn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/16 10:58
 */
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名获取用户信息，注册判断用户名唯一性，登录和修改密码用
     * @param username
     * @return
     */
    @Override
    public User queryByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = (User) userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 用户注册业务，加事务注意遵循阿里规范
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int register(User user) {
        //判断用户是否唯一
        String username = user.getUsername();
        User userByUsername = this.queryByUsername(username);
        if (userByUsername != null) {
            //返回-1说明已经有用户注册了该用户名重新输入用户名
            return -1;
        }
        //判断邮箱是否被注册
        QueryWrapper emailQueryWrapper = new QueryWrapper();
        emailQueryWrapper.eq("email", user.getEmail());
        User userByEmail = (User) userMapper.selectOne(emailQueryWrapper);
        if (userByEmail != null) {
            //返回-2说明该邮箱被注册了
            return -2;
        }
        //给用户密码加密
        user.setPassword(BcryptUtils.hashPassword(user.getPassword()));
        return userMapper.insert(user);
    }

    /**
     * 修改密码业务
     * @param username
     * @param password
     * @return
     */
    @Override
    public User updatePassword(String username, String password) {
        User user = this.queryByUsername(username);
        user.setPassword(BcryptUtils.hashPassword(password));
        userMapper.updateById(user);
        return user;
    }

    /**
     * 根据用户名和密码登录,传过来的user对象里只有username和password
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        //根据用户名查询是否存在该用户
        User dateUser = this.queryByUsername(user.getUsername());
        //如果查到了则比较密码
        if (dateUser != null) {
            //判断密码是否正确
            boolean result = BcryptUtils.checkPassword(user.getPassword(), dateUser.getPassword());
            //如果正确返回查询到的user对象
            if (result) {
                return dateUser;
            }
        }
        return null;
    }
}
