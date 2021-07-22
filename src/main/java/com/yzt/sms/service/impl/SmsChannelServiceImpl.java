package com.yzt.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.entity.SmsChannel;
import com.yzt.sms.mapper.SmsChannelMapper;
import com.yzt.sms.service.SmsChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * 通道实现类
 *
 * @author Yin ZT
 * @projectName sms
 * @date 2021/07/18 21:58
 **/
@Service
public class SmsChannelServiceImpl implements SmsChannelService {

    SmsChannelMapper smsChannelMapper;

    @Autowired
    public SmsChannelServiceImpl(SmsChannelMapper smsChannelMapper){
        this.smsChannelMapper = smsChannelMapper;
    }

    @Override
    public SmsChannel insertSmsChannel(SmsChannel smsChannel) {
        smsChannelMapper.insert(smsChannel);
        return smsChannel;
    }

    @Override
    public int deleteByChannelId(SmsChannel smsChannel) {
        return smsChannelMapper.deleteById(smsChannel);
    }

    @Override
    public SmsChannel selectChannel(SmsChannel smsChannel) {
        QueryWrapper<SmsChannel> channelWrapper = new QueryWrapper<SmsChannel>();
        channelWrapper.eq(StringUtils.isNotBlank(smsChannel.getChannelName()),"channel_name",smsChannel.getChannelName());
        channelWrapper.eq(StringUtils.isNotBlank(smsChannel.getChannelToken()),"channel_token",smsChannel.getChannelToken());
        channelWrapper.eq(null != smsChannel.getChannelId(),"channel_id",smsChannel.getChannelId());
        channelWrapper.eq(StringUtils.isNotBlank(smsChannel.getChannelEncryptionKey()),"channel_encryption_key",smsChannel.getChannelEncryptionKey());
        channelWrapper.eq(null != smsChannel.getUserId(),"user_id",smsChannel.getUserId());

        return smsChannelMapper.selectOne(channelWrapper);
    }

    @Override
    public List<SmsChannel> selectChannelList(SmsChannel smsChannel) {
        QueryWrapper<SmsChannel> channelWrapper = new QueryWrapper<SmsChannel>();
        channelWrapper.eq("user_id",smsChannel.getUserId());
        return smsChannelMapper.selectList(channelWrapper);

    }
}
