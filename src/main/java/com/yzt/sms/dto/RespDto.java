package com.yzt.sms.dto;

import com.yzt.sms.enums.RespCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author yin.ZT
 */
@Data
@AllArgsConstructor
public class RespDto<T> implements Serializable {

    private String code;
    private String msg;
    private T data;

    public RespDto(RespCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public RespDto(RespCode  code, String msg, T data) {
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public RespDto(RespCode code,T data){
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public RespDto(RespCode code, Object... moreMsg) {
        this.code = code.getCode();
        try {
            this.msg = code.getRespMsg(moreMsg);
        } catch (Exception e) {
            this.msg = code.getMsg();
        }
    }

    public RespDto(T data) {
        this.code = RespCode.SUCCESS.getCode();
        this.msg = RespCode.SUCCESS.getMsg();
        this.data = data;
    }

}