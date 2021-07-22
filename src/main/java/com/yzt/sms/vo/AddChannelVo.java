package com.yzt.sms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 增加通道Vo
 *
 * @author Yin ZT
 * @projectName sms
 * @date 2021/07/18 20:34
 **/
@Data
public class AddChannelVo implements Serializable {

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道加密密码
     */
    private String channelEncryptionKey;
}
