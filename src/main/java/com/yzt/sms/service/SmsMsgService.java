package com.yzt.sms.service;

import com.yzt.sms.entity.SmsMessage;

import java.util.List;

/**
 * 消息服务层
 *
 * @author yin.ZT
 * @date 2021/07/20 17:19
 **/
public interface SmsMsgService {
    SmsMessage insertSmsMsg(SmsMessage smsMessage);


    List<SmsMessage> selectSmsMsgList(SmsMessage smsMessage);

    int deleteSmsMsgList(SmsMessage smsMessage);
}
