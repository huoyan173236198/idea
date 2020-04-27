package com.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/20 22:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {
    /**
     * 发给谁
     */
    private String to;

    /**
     * 标题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;
}
