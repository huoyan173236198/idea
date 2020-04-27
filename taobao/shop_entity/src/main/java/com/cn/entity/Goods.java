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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/3/21 16:09
 */
@ApiModel("商品信息类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("商品ID")
    private Integer id;

    @ApiModelProperty("商品名")
    private String gname;

    @ApiModelProperty("商品信息")
    private String ginfo;

    @ApiModelProperty("商品图片")
    private String gimage;

    @ApiModelProperty("商品价格")
    private BigDecimal gprice;

    @ApiModelProperty("商品类型ID")
    private Integer tid;

    @ApiModelProperty("商品库存")
    private Integer gsave;

    @ApiModelProperty("商品创建时间")
    private Date createtime = new Date();

    @ApiModelProperty("商品状态")
    private Integer status;
}
