package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.entity.Email;
import com.cn.entity.User;
import com.cn.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/15 20:14
 */
@Controller("/sso")
public class SSOController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Reference
    private IUserService userService;
    /**
     *去注册页面
     */
    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    /**
     *去登录页面
     */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录过程
     */
    @RequestMapping("/login")
    public String login(User user, String returnUrl, HttpServletResponse response) {
        //user重新指向查询获得的user对象
        user = userService.login(user);
        //判断是否查到登录的用户
        if(user==null){
            //没有查到重定向登录页面返回错误代码
            return "redirect:/sso/tologin?error=1";
        }
        //判断跳转到登录页面前的页面以便登录后返回到登录前的页面
        if(returnUrl==null||returnUrl.trim().equals("")){
            //如果登录前没有去其他页面则登录后跳转到首页
            returnUrl= "http://192.168.17.1:8084";
        }
        //生成一个令牌做为key将登录用户user放入到redis中
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, user);
        //该令牌和cookie中的令牌过期时间都设置为7天
        redisTemplate.expire(token, 7, TimeUnit.DAYS);
        //设置cookie
        Cookie cookie = new Cookie("loginToken", token);
        //设置令牌为一周
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:"+returnUrl;
    }
    /**
     * 注册过程
     */
    @RequestMapping("/register")
    public String register(User user,int code){
        //根据email获取redis中的验证码
        Integer saveCode = (Integer) redisTemplate.opsForValue().get(user.getEmail() + "_code");
        //判断验证码是否存在或者是否正确
        if(saveCode==null||saveCode!=code){
            return "redirect:/sso/toregister?erro=-3";
        }
        //执行注册添加用户
        int register = userService.register(user);
        //判断添加用户后的返回值是否大于0
        if(register>0){
            //大于0说明添加成功跳转登录页面
            return "redirect:/sso/tologin";
        }
        //通过验证码验证执行注册时异常返回异常信息
        return "redirect:/sso/toregister?erro="+register;
    }

    /**
     * 发送验证码到邮箱
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String email){
        String content = "注册验证码为：%d，如果不是本人操作请忽略";
        //产生一个随机四位数放入email中和redis中
        int code = (int) (Math.random() * 9000 + 1000);
        content = String.format(content, code);
        //封装email对象
        Email emailObj = new Email(content, "淘宝注册验证码", content);
        //将验证码放入redis中
        redisTemplate.opsForValue().set(email + "code", code);
        //设置验证码过期时间
        redisTemplate.expire(email + "code", 10, TimeUnit.MINUTES);
        //将email对象放入email_queue队列中
        rabbitTemplate.convertAndSend("email_exchange",emailObj);
        return "successful";
    }
    /**
     * 去忘记密码页面
     */
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetPassword";
    }

    /**
     * 使用ajax发送修改密码邮件
     */
    @RequestMapping("/sendPassMail")
    @ResponseBody
    public Map<String, Object> sendPassMail(String username) {
        Map<String, Object> map = new HashMap<>();
        //根据用户名查询是否存在该用户
        User user = userService.queryByUsername(username);
        if(user==null){
            map.put("code", "1000");
            return map;
        }
        //用户存在发送邮件
        //设置一个修改密码的凭证放入到redis中
        //生成凭证
        String token = UUID.randomUUID().toString();
        //凭证放入redis中
        redisTemplate.opsForValue().set(username + "_token", token);
        redisTemplate.expire(username + "_token", 5, TimeUnit.MINUTES);
        //生成邮件中跳转的url
        String url = "http://192.168.17.1:8085/sso/toChangePassword?usernmae=" + username + "&token=" + token;
        //封装发送的邮件对象
        Email email = new Email(user.getEmail(),
                "淘宝找回密码邮件",
                "找回密码请点击<a href='"+url+"'>点击这里</a>");
        //将邮件对象放入队列中
        rabbitTemplate.convertAndSend("email_exchange", email);
        //发送成功返回脱敏后的邮箱
        String emailStr = user.getEmail();
        int index = emailStr.indexOf("@");
        //获得脱敏邮箱，将下标为3和@下标之间的内容替换为脱敏符
        String emailStr2 = emailStr.replace(emailStr.substring(3, index), "*******");
        //设置邮箱的跳转地址,去到用户邮箱登录的首页，例如：mail.sina.com.cn是新浪邮箱的登录首页或者mail.qq.com是qq邮箱的登录首页
        String gomail = "mail." + emailStr.substring(index + 1);
        map.put("code", "0000");
        map.put("emailStr", emailStr2);
        map.put("gomail", gomail);
        return map;
    }

    /**
     *接收邮件发送的请求返回修改密码页面，在该页面可以获得邮箱中发送的请求中的username和token
     * @return
     */
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "changePassword";
    }

    /**
     * 获取修改密码页面传回的数据进行后台修改密码业务
     *
     * @return
     */
    @RequestMapping("/changePassword")
    public String changePassword(String username, String password, String token) {
        //根据用户名获取redis中的令牌，username和token是绑定的
        String uToken = (String) redisTemplate.opsForValue().get(username + "_token");
        //接收到的该用户令牌和数据库中的用户令牌相同则可以修改密码
        if(token.equals(uToken)){
            userService.updatePassword(username, password);
            //修改后删除username绑定的token
            redisTemplate.delete(username + "_token");
            return "redirect:/sso/tologin";
        }
        return "fail";
    }
    /**
     * 添加用户登录判断业务2
     */

}
