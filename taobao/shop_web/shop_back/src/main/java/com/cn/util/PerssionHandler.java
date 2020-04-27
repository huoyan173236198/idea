package com.cn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/15 18:44
 */
@Component
//该类中的方法是实现权限控制的方法，需要自定义，其他的boolean方法全部设置为返回值true
public class PerssionHandler {

    public static final Logger log = LoggerFactory.getLogger(PerssionHandler.class);

    public boolean hasPerssion(HttpServletRequest request, Authentication authentication) {
        log.info("调用了PerssionHandler类**********************************");
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            log.info("已经认证通过");
            String requestURI = request.getRequestURI();
            log.info("请求路径:"+requestURI);
            UserDetails userDetails = (UserDetails) principal;
            log.info("PerssionHandler收到的认证用户"+userDetails.toString());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                log.info("获得的权限：" + authority.getAuthority());
                if (antPathMatcher.match(requestURI, authority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }
}
