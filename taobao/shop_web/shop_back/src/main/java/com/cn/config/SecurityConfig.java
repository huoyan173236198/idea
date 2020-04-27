package com.cn.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.service.IBackUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/15 17:19
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference
    private IBackUserService backUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //去登录界面
                .formLogin().loginPage("/tologin")
                //如果请求路径是该路径的话则说明是去验证登录用户的过程
                .loginProcessingUrl("/login")
                //验证失败后
                .failureUrl("/tologin?error=1")
                //之前的操作不用控制全部放行
                .permitAll()
                .and()
                //注销不用权限验证
                .logout().permitAll()
                .and()
                .authorizeRequests()
                // 放行Security相关与swagger2的请求
                .antMatchers("/swagger-ui.html").permitAll()

                .antMatchers("/swagger-resources/**").permitAll()

                .antMatchers("/images/**").permitAll()

                .antMatchers("/webjars/**").permitAll()

                .antMatchers("/v2/api-docs").permitAll()

                .antMatchers("/configuration/ui").permitAll()

                .antMatchers("/configuration/security").permitAll()

                .mvcMatchers("/resources/**").permitAll()


                //通过身份认证后放行，也就是登录成功后，但是不用通过权限认证
                .mvcMatchers("/").authenticated()
                .anyRequest().access("@perssionHandler.hasPerssion(request,authentication)")
                //.anyRequest().authenticated()
                .and()
                //csrf又称跨域请求伪造，攻击方通过伪造用户请求访问受信任站点
                .csrf().disable()
                //处理iframe请求，让security放行,前端页面是一个iframe框架会被拦截，可以在前端页面查找frame关键字查找该标签
                // 这里加一个请求头就可以解决这个问题，这是security的一个安全机制，防止通过iframe在浏览器页面内部绕过权限控制，防止被黑
                .headers().frameOptions().sameOrigin();

    }

    /**
     *调用的身份认证实现方法
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(backUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
