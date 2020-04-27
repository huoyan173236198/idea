package com.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2019/12/2 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("后台用户实体类")
@TableName("back_user")
/**
 *
 实现UserDetails中的方法来告诉SpringSurecyiy使用该实体类来作为权限控制的实体类
 */
public class BackUser implements Serializable, UserDetails {
    @ApiModelProperty("后台用户ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("后台用户名")
    private String username;
    @ApiModelProperty("后台用户密码")
    private String password;
    @ApiModelProperty("后台用户姓名")
    private String name;
    @ApiModelProperty("后台用户性别")
    private Integer sex;
    @ApiModelProperty("后台用户创建时间")
    private Date createtime = new Date();
    @ApiModelProperty("后台用户状态")
    private Integer status;

    @ApiModelProperty("当前用户的角色")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty("当前用户的权限")
    @TableField(exist = false)
    private List<Power> powers;

    //以下都是实现UserDetails中的方法，其中还有getUsername和getPassword方法需要重写，不过由于@Data中重写过了所以不用重写
    //第一个方法是获得权限的方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (powers != null && powers.size() > 0) {
            for (Power power : powers) {
                if (power.getPowerpath() != null && !power.getPowerpath().equals("")) {
                    authorities.add(new SimpleGrantedAuthority(power.getPowerpath()));
                }
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
