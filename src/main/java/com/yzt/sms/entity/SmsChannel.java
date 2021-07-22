package com.yzt.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 通道信息
 * @author
 * @date 15:34 2021/7/18
**/
@Data
@TableName(value = "sms_channel")
public class SmsChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
    * 主键
    */
    private Integer channelId;

    /**
    * 使用用户id
    */
    private Integer userId;

    /**
    * 通道token
    */
    private String channelToken;

    /**
    * 通道名称
    */
    private String channelName;

    /**
     * 通道加密密码
     */
    private String channelEncryptionKey;

    /**
    * 生成时间
    */
    private Date createTime;

    /**
     * 是否被删除
     **/
    @TableLogic
    private Integer isDeleted;

    public SmsChannel() {}
}