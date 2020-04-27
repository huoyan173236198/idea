package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.service.IBackUserService;
import com.cn.entity.BackUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/10 17:44
 */
@Api(description = "后台用户管理接口")
@Controller
@RequestMapping("/buser")
public class BackUserController {

    public static final Logger log = LoggerFactory.getLogger(BackUserController.class);

    @Reference
    private IBackUserService backUserService;

    @ApiOperation("后台用户查询")
    @GetMapping("/list")
    public String userList(Model model) {
        log.info("调用用户查询controller");
        List<BackUser> backUsers=backUserService.quarryAll();
        model.addAttribute("users", backUsers);
        return "buserlist";
    }

    @ApiOperation("添加用户")
    @PostMapping("/insert")
    public String addUser(BackUser backUser) {
        log.info("添加用户controller");
        backUserService.insertUser(backUser);
        return "redirect:/buser/list";
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public String deleteUser(BackUser backUser) {
        log.info("删除用户controller");
        backUserService.deleteUser(backUser);
        return "redirect:/buser/list";
    }

    @ApiOperation("修改用户")
    @PostMapping("/update")
    public String updateUser(BackUser backUser) {
        log.info("修改用户controller");
        backUserService.updateUser(backUser);
        return "redirect:/buser/list";
    }
    //在执行该方法前需要先查询出该用户的所有角色，通过ajax在RoleController中进行查询
    @ApiOperation("修改用户角色")
    @PostMapping("/updaterole")
    public String updateUserRole(Integer uid,Integer[] rid){
        log.info("修改用户角色controller");
        log.info("操作用户："+uid+",添加角色："+ Arrays.toString(rid));
        backUserService.updateUserRole(uid, rid);
        return "redirect:/buser/list";
    }
}
