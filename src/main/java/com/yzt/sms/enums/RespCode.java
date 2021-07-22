package com.yzt.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 10:57
 * @ProjectName transms
 * @Discription 1243
 **/
@Getter
@AllArgsConstructor
public enum RespCode  {
    //操作成功
    SUCCESS("1","操作成功"),
    //登录/注册成功
    LOGIN_REGISTER_SUCCESS("1","登录成功"),
    //删除账户成功
    DELETE_ACCOUNT_SUCCESS("2","账户删除成功"),
    //服务器错误
    SERVICE_MISTAKE("9999","服务器错误"),
    //登录/注册异常
    LOGIN_REGISTER_FAILED("9998","注册/登录失败，密码错误或服务器错误"),
    //密钥和用户不匹配
    WRONG_SECRET_KEY("9997","密钥和用户不匹配，如果遗忘请联系管理员"),
    TOKEN_NONEXISTENT("9996","令牌过期或不存在");


    private final String code;
    private final String msg;


    public String getRespMsg(Object... args) {
        if (args == null){
            return msg;
        }else {
            return MessageFormat.format(msg, args);
        }
    }

}
