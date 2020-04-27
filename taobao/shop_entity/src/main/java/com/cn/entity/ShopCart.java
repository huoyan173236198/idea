package com.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/20 10:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCart implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer gid;
    private Integer uid;
    private Integer gnumber;
    private BigDecimal sprice;
    private Date createtime = new Date();
    private Integer status;
}
