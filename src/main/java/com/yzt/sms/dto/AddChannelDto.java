package com.yzt.sms.dto;

import lombok.Data;

/**
 * 新增通道dto
 *
 * @author Yin ZT
 * @projectName sms
 * @date 2021/07/18 22:08
 **/
@Data
public class AddChannelDto {
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
}
