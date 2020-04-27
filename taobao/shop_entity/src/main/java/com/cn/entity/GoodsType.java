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

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/22 16:03
 */
@ApiModel("商品类型实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("gtype")
public class GoodsType implements Serializable {
    @ApiModelProperty("商品类型ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品类型父ID")
    private Integer pid;

    @ApiModelProperty("商品类型名")
    private String gtypename;

    @ApiModelProperty("商品类型创建时间")
    private Date createtime = new Date();

    @ApiModelProperty("商品类型状态")
    private Integer status;

    @ApiModelProperty("是否具备该类型")
    @TableField(exist = false)
    private boolean checked;
}
