package com.yzt.sms.vo;

import lombok.Data;

/**
 * 根据token修改密码Vo
 *
 * @author 17566
 * @projectName sms
 * @date 2021/07/18 10:14
 **/
@Data
public class ResetPassWordByTokenVo {
    /**
     * 密码
     */
    private String password;

    /**
     * 信息存储用token
     */
    private String token;
}
