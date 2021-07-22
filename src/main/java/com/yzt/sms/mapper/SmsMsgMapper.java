package com.yzt.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzt.sms.entity.SmsMessage;
import org.springframework.stereotype.Repository;

/**
 * 信息mapper
 *
 * @author yin.ZT
 * @date 2021/07/20 15:11
 **/
@Repository
public interface SmsMsgMapper extends BaseMapper<SmsMessage> {
}
