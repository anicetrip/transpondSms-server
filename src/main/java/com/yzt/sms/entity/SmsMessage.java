package com.yzt.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息控制
 *
 * @author yin.ZT
 * @date 2021/07/20 15:09
 **/
@Data
@TableName(value = "sms_message")
public class SmsMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通道名
     */
    private String channelToken;

    /**
     * 来源手机
     */
    private String fromPlace;

    /**
     * 内容
     */
    private String content;

    /**
     * 插入时间
     */
    private Date timestamp;

    /**
     * 签名
     */
    private String sign;

    /**
     * 插入时间
     */
    private Date createTime;

    /**
     * 是否被删除
     **/
    @TableLogic
    private Integer isDeleted;

}