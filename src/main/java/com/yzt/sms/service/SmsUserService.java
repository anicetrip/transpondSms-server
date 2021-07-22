package com.yzt.sms.service;

import com.yzt.sms.entity.SmsUserEntity;
import org.springframework.stereotype.Service;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 11:25
 * @ProjectName transms
 * @Discription sms用户service
 **/

public interface SmsUserService {
    /**
     * 查询
     * @author yin.ZT
     * @date 15:17 2021/7/16
     * @param smsUserEntity entity
     * @return SmsUserEntity
     **/
    SmsUserEntity selectSmsUser(SmsUserEntity smsUserEntity);

    /**
     * 插入用户信息
     * @author yin.ZT
     * @date 15:23 2021/7/16
     * @param smsUserEntity  entity
     * @return SmsUserEntity
     **/
    SmsUserEntity insertSmsUser(SmsUserEntity smsUserEntity);

    int deleteSmsUser(String token);

    int updateSmsUser(SmsUserEntity smsUserJudgeExist);
}
