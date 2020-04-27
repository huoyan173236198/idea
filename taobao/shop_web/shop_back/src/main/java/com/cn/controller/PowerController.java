package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.service.IPowerService;
import com.cn.entity.Power;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/13 13:38
 */
@Api(description = "权限接口")
@Controller
@RequestMapping("/power")
public class PowerController {

    public static final Logger log = LoggerFactory.getLogger(PowerController.class);

    @Reference
    private IPowerService powerService;

    @ApiOperation("权限列表")
    @GetMapping("/list")
    public String powerList(Model model){
        log.info("查看权限列表");
        List<Power> powers = powerService.powerList();
        model.addAttribute("powers", powers);
        return "powerlist";
    }

    @ApiOperation("添加权限")
    @PostMapping("/insert")
    public String insertPower(Power power){
        log.info("添加权限");
        powerService.insertPower(power);
        return "redirect:/power/list";
    }

    @ApiOperation("修改权限")
    @PostMapping("/update")
    public String updatePower(Power power){
        log.info("修改权限");
        powerService.updatePower(power);
        return "redirect:/power/list";
    }

    @ApiOperation("删除权限")
    @PostMapping("/delete")
    public String deletePower(Power power) {
        log.info("删除权限");
        powerService.deletePower(power);
        return "redirect:/power/list";
    }

    @ApiOperation("通过ajax查询角色权限")
    @GetMapping("/listajax")
    @ResponseBody
    public List<Power> ajaxList(Integer rid) {
        log.info("通过ajax查询角色权限");
        List<Power> listajax = powerService.listajax(rid);
        return listajax;
    }

    //@ApiOperation("通过ajax查询权限树")
    @ResponseBody
    @GetMapping("/listajaxpowertree")
    public List<Power> listajaxpowertree(){
        return powerService.powerList();
    }


}
