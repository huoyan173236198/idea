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
import java.util.Date;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/9 17:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("权限实体类")
public class Power implements Serializable {
    @ApiModelProperty("权限ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("父权限ID")
    private Integer pid = -1;
    @ApiModelProperty("权限名")
    private String powername;
    @ApiModelProperty("权限路径")
    private String powerpath;
    @ApiModelProperty("权限创建时间")
    private Date createtime = new Date();
    @ApiModelProperty("权限状态")
    private Integer status;

    @ApiModelProperty("父权限名")
    @TableField(exist = false)
    private String pname;
    @ApiModelProperty("当前角色是否拥有该权限")
    @TableField(exist = false)
    private boolean checked;

}
