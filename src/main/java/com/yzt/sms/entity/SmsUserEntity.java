package com.yzt.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 10:24
 * @ProjectName transms
 * @Discription TODO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sms_user")
public class SmsUserEntity  {


    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 手机号
     */
    private Long loginPhone;

    /**
     * 邮箱
     */
    private String loginEmail;

    /**
     * 密码
     */
    private String password;

    /**
     * 信息存储用token
     */
    private String token;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *找回密码用密钥
     **/
    private String secretKey;

    /**
     * 是否被删除
     **/
    @TableLogic
    private Integer isDeleted;
}
