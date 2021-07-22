package com.yzt.sms.dto;

import lombok.Data;

/**
 * 修改密码后返回内容
 *
 * @author 17566
 * @projectName sms
 * @date 2021/07/18 00:01
 **/
@Data
public class ResetPassWordBySecretKeyDto {

    /**
     * 信息存储用token
     */
    private String token;

    /**
     *找回密码用密钥
     **/
    private String secretKey;

    /**
     * 密码
     */
    private String password;
}
