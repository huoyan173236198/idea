package com.cn.shop_back;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.entity.BackUser;
import com.cn.entity.Power;
import com.cn.service.IBackUserService;
import com.cn.service.IPowerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//在测试包的启动类中添加@RunWith(SpringRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopBackApplicationTests {
    //这里由于是用的dubbo所以是@Reference
    @Reference
    private IBackUserService backUserService;
    @Reference
    private IPowerService powerService;
    @Test
    //测试方法必须是无参数无返回值的
    public void updateUserRole(){
        Integer uid = 1;
        //声明一个Integer数组的方式
        Integer[] rid = new Integer[]{1, 2, 3};
        backUserService.updateUserRole(uid, rid);

    }

    @Test
     public void TestUserList(){
        List<BackUser> backUsers = backUserService.quarryAll();
        System.out.println("用户列表"+backUsers.toString());
    }

    @Test
    public void TestPowerList(){
        List<Power> powers = powerService.powerList();
        System.out.println(powers.toString());
    }


}
