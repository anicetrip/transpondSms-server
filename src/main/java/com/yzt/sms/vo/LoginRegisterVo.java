package com.yzt.sms.vo;

import lombok.Data;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 10:49
 * @ProjectName transms
 * @Discription TODO
 **/
@Data
public class LoginRegisterVo {
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
}
