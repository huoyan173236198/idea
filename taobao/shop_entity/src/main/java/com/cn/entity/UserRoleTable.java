package com.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/10 19:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//用户角色中间表
public class UserRoleTable implements Serializable {
    //用户ID
    private Integer uid;
    //角色ID集合
    private Integer rid;
}
