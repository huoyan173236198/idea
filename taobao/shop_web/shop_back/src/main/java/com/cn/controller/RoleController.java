package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.service.IRoleService;
import com.cn.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/11 11:42
 */
@Api(description = "后台角色接口")
@Controller
@RequestMapping("/role")
public class RoleController {

    public static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Reference
    private IRoleService roleService;

    @ApiOperation("后台角色查询列表")
    @GetMapping("/list")
    public String roleList(Model model) {
        log.info("角色查询");
        List<Role> roles = roleService.roleList();
        model.addAttribute("roles", roles);
        return "rolelist";
    }

    @ApiOperation("角色添加")
    @PostMapping("/insert")
    public String inserRole(Role role) {
        log.info("角色添加");
        roleService.insertRole(role);
        return "redirect:/role/list";
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    public String updateRole(Role role) {
        log.info("修改角色");
        roleService.updateRole(role);
        return "redirect:/role/list";
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public String deleteRole(Role role) {
        log.info("删除角色");
        roleService.deleteRole(role);
        return "redirect:/role/list";
    }

    //通过ajax查询用户是否拥有角色
    @ApiOperation("ajax查询用户角色")
    @GetMapping("/listajax")
    @ResponseBody
    public List<Role> ajaxRoleList(Integer uid) {
        log.info("ajax查询用户是否拥有角色");
        List<Role> roles = roleService.listajax(uid);
        return roles;
    }


    //在执行该方法前需要先通过ajax获取该角色的所有权限，该方法在PowerController中定义
    @ApiOperation("修改角色权限")
    @PostMapping("/updatepower")
    @ResponseBody
    public String updateRolePower(Integer rid, @RequestParam("pids[]") Integer[] pids) {
        log.info("修改角色权限");
        roleService.updateRolePower(rid, pids);
        return "successful";
    }
}
