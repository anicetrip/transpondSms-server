package com.yzt.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.entity.SmsUserEntity;
import com.yzt.sms.mapper.SmsUserMapper;
import com.yzt.sms.service.SmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yin ZT
 * @data 2021/7/16 11:26
 * @projectName transms
 *  用户实现类
 **/
@Service
public class SmsUserServiceImpl implements SmsUserService {

    SmsUserMapper smsUserMapper;
    @Autowired
    public SmsUserServiceImpl(SmsUserMapper smsUserMapper){
        this.smsUserMapper = smsUserMapper;
    }


    /**
     * 查询用户信息
     * @author yin.ZT
     * @date 15:31 2021/7/16
     * @param smsUserEntity  包含电话/邮箱信息
     * @return SmsUserEntity
     **/
    @Override
    public SmsUserEntity selectSmsUser(SmsUserEntity smsUserEntity) {
        QueryWrapper<SmsUserEntity> smsUserEntityQueryWrapper = new QueryWrapper<>();
        smsUserEntityQueryWrapper.eq(StringUtils.isNotBlank(smsUserEntity.getLoginEmail()),"login_email",smsUserEntity.getLoginEmail());
        smsUserEntityQueryWrapper.eq(null != smsUserEntity.getLoginPhone(),"login_phone",smsUserEntity.getLoginPhone());
        smsUserEntityQueryWrapper.eq(null != smsUserEntity.getUserId(),"user_id",smsUserEntity.getUserId());
        smsUserEntityQueryWrapper.eq(StringUtils.isNotBlank(smsUserEntity.getPassword()),"password",smsUserEntity.getPassword());
        smsUserEntityQueryWrapper.eq(StringUtils.isNotBlank(smsUserEntity.getSecretKey()), "secret_key", smsUserEntity.getSecretKey());
        smsUserEntityQueryWrapper.eq(StringUtils.isNotBlank(smsUserEntity.getToken()),"token",smsUserEntity.getToken());

        return smsUserMapper.selectOne(smsUserEntityQueryWrapper);
    }

    @Override
    public SmsUserEntity insertSmsUser(SmsUserEntity smsUserEntity) {
        smsUserMapper.insert(smsUserEntity);
        return smsUserEntity;
    }

    @Override
    public int deleteSmsUser(String token) {
        QueryWrapper<SmsUserEntity> smsUserEntityQueryWrapper = new QueryWrapper<>();
        smsUserEntityQueryWrapper.eq("token",token);
        return smsUserMapper.delete(smsUserEntityQueryWrapper);
    }

    @Override
    public int updateSmsUser(SmsUserEntity smsUserJudgeExist) {
        final int i = smsUserMapper.updateById(smsUserJudgeExist);
        return i;
    }


}

