package com.yzt.sms.service;

import com.yzt.sms.entity.SmsChannel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yin ZT
 * @projectName sms
 * @date 2021/07/18 21:57
 **/
@Service
public interface SmsChannelService {
    SmsChannel insertSmsChannel(SmsChannel smsChannel);

    int deleteByChannelId(SmsChannel smsChannel);

    SmsChannel selectChannel(SmsChannel smsChannel);

    List<SmsChannel> selectChannelList(SmsChannel smsChannel);
}
