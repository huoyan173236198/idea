package com.cn.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.dao.UserRoleMapper;
import com.cn.entity.BackUser;
import com.cn.service.IBackUserService;
import com.cn.dao.BackUserMapper;
import com.cn.entity.UserRoleTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2019/12/12 21:53
 */
@Service
public class BackUserServiceImpl implements IBackUserService {
    //声明一个日志记录器对象
    public static final Logger log = LoggerFactory.getLogger(BackUserServiceImpl.class);
    @Autowired
    private BackUserMapper backUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    //无参构造
    private BackUserServiceImpl() {
        log.info("调用了用户服务");
    }

    //查询所有用户列表
    @Override
    public List<BackUser> quarryAll() {
        log.info("调用了用户查询服务");
        return backUserMapper.selectList(null);
    }

    //登录用户的查询，由于使用权限控制所以在下面的UserDetails中实现
   /* @Override
    public BackUser login(String username, String password) {
        System.out.println("扫描到了BackUserServiceImpl类的login方法");
        BackUser backUser = backUserMapper.queryByUserName(username);
        System.out.println("用户名" + backUser.getUsername() + ";密码" + backUser.getPassword());
        if (backUser != null && backUser.getPassword().equals(password)) {
            //登录成功
            return backUser;
        }
        return null;
    }*/

    //添加用户
    @Override
    public int insertUser(BackUser backUser) {
        log.info("调用了添加用户的服务");
        return backUserMapper.insert(backUser);
    }

    //修改用户信息
    @Override
    public int updateUser(BackUser backUser) {
        log.info("调用了修改用户的服务");
        return backUserMapper.updateById(backUser);
    }

    //删除用户
    @Override
    public int deleteUser(BackUser backUser) {
        log.info("调用了删除用户服务");
        return backUserMapper.deleteById(backUser.getId());
    }

    //添加用户角色,由于业务逻辑复杂需要事务管理
    //遇到异常自动回滚，例如删除成功后添加遇到异常那么一起回滚到删除前
    @Override
    @Transactional
    public int updateUserRole(Integer uid, Integer[] rid) {
        log.info("调用添加用户角色服务");
        //先删除该角色绑定的所有角色
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        userRoleMapper.delete(queryWrapper);

        //重新添加新的用户角色关系
        for (Integer roleid : rid) {
            UserRoleTable userRoleTable = new UserRoleTable(uid,roleid);
            userRoleMapper.insert(userRoleTable);
        }
        return 0;
    }

    //使用UserDetailsService接口中的UserDetails方法来告诉SpringSurecity使用该方法去查Dao层
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在该方法中username会通过实现UserDetails的BackUser的getUserame方法自动获取，所以username和BackUser的username字段名要一致
        // 会自动比较password
        BackUser backUser = backUserMapper.queryByUserName(username);
        System.out.println("用户名" + backUser.getUsername() + ";密码" + backUser.getPassword());
        log.info("用户角色:"+backUser.getRoles().toString());
        log.info("用户权限:"+backUser.getPowers().toString());
        if (backUser == null) {
            //登录成功
            log.info("该用户不存在："+username);
            throw new UsernameNotFoundException("该用户不存在");
        }
        return backUser;
    }
}
