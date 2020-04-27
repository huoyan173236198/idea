package com.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/9 17:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色实体类")
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("角色ID")
    private Integer id;
    @ApiModelProperty("角色名")
    private String rolename;
    @ApiModelProperty("角色别名")
    private String rolealias;
    @ApiModelProperty("角色创建时间")
    private Integer createtime;
    @ApiModelProperty("角色状态")
    private Integer status;

    @ApiModelProperty("是否拥有角色")
    @TableField(exist = false)
    private boolean checked;
}
