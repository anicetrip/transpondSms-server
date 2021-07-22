package com.yzt.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.entity.SmsMessage;
import com.yzt.sms.mapper.SmsMsgMapper;
import com.yzt.sms.service.SmsMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务层 实现
 *
 * @author yin.ZT
 * @date 2021/07/20 17:20
 **/
@Service
public class SmsMsgServiceImpl implements SmsMsgService {

    @Autowired
    SmsMsgMapper smsMsgMapper;


    @Override
    public SmsMessage insertSmsMsg(SmsMessage smsMessage) {
        smsMsgMapper.insert(smsMessage);
        return smsMessage;
    }

    @Override
    public List<SmsMessage> selectSmsMsgList(SmsMessage smsMessage) {
        QueryWrapper<SmsMessage> smsMessageQueryWrapper = new QueryWrapper<>();
        smsMessageQueryWrapper.eq(StringUtils.isNotBlank(smsMessage.getChannelToken()),"channel_Token", smsMessage.getChannelToken());
        return smsMsgMapper.selectList(smsMessageQueryWrapper);
    }

    @Override
    public int deleteSmsMsgList(SmsMessage smsMessage) {
        QueryWrapper<SmsMessage> smsMessageQueryWrapper = new QueryWrapper<>();
        smsMessageQueryWrapper.lt("id",smsMessage.getId());
        smsMessageQueryWrapper.eq("channel_token",smsMessage.getChannelToken());
        return smsMsgMapper.delete(smsMessageQueryWrapper);
    }


}
