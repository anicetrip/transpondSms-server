package com.yzt.sms.dto;

import lombok.Data;

/**
 * 根据token修改密码
 *
 * @author 17566
 * @projectName sms
 * @date 2021/07/18 10:38
 **/
@Data
public class ResetPassWordByTokenDto {
    /**
     * 密码
     */
    private String password;

    /**
     * 信息存储用token
     */
    private String token;
}
