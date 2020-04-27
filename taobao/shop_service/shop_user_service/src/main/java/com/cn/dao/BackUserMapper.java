package com.cn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.BackUser;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2019/12/12 22:13
 */
public interface BackUserMapper extends BaseMapper<BackUser> {
    //通过用户名查询该用户的相关信息
    BackUser queryByUserName(String username);





}
