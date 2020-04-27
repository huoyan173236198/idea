package com.cn.shop_user_service;

import com.cn.dao.RoleMapper;
import com.cn.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@MapperScan("com.cn.dao")
@RunWith(SpringRunner.class)
@SpringBootTest
class ShopUserServiceApplicationTests {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    void listajax() {
        Integer uid = 3;
        List<Role> roles = roleMapper.queryRoleByUid(uid);
        for (Role role : roles) {
            System.out.println("其查询结果："+role.toString());
        }
    }

}
