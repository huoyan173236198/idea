package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.service.IBackUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2019/12/1 20:51
 */
//@Api(description = "后台用户登录接口")
//将登录的用户信息放入到session中
@Api(description = "登录接口")
@Controller
//@SessionAttributes("loginUser")
public class LoginController {

    @Reference
    private IBackUserService backUserService;

    //声明一个日志记录器对象
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);
    /**
     * 跳转到登录页面
     */
    @ApiOperation("跳转到登录页面")
    @GetMapping("/tologin")
    public String toLogin() {
        log.info("用户已经来到登录页面");
        return "login";
    }

    /**进行登录*/
    //该方法交给Seurity内部自动处理
    /*@RequestMapping("/login")
    @ApiOperation("验证登录用户跳转页面")
    public String login(String username,String password , Model model) {
        log.info("登录用户输入的用户名："+username+"-------输入的密码："+password);
        BackUser backUser = backUserService.login(username, password);
        System.out.println("用户名"+backUser.getUsername()+";密码"+backUser.getPassword());

        if (backUser != null) {
            model.addAttribute("loginUser",backUser);
            log.info("用户登录状态：Successful");
            return "index";
        }
        log.info("用户登录状态：Faile");
        return "redirect:/toLogin?error=1";
    }*/
}
