package com.yzt.sms.vo;

import lombok.Data;

/**
 * 根据密钥修改密码
 *
 * @author 17566
 * @projectName sms
 * @date 2021/07/17 23:39
 **/
@Data
public class ResetPassWordBySecretKeyVo {
    /**
     * 手机号
     */
    private Long loginPhone;

    /**
     * 邮箱
     */
    private String loginEmail;

    /**
     *找回密码用密钥
     **/
    private String secretKey;

    /**
     * 密码
     */
    private String password;
}
