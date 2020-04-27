package com.cn.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/11 12:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//角色权限中间表
public class RolePowerTable implements Serializable {
    //角色ID
    private Integer rid;
    //权限ID
    private Integer pwid;

}
