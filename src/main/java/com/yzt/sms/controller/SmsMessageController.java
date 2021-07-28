package com.yzt.sms.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.config.WebSocketServer;
import com.yzt.sms.dto.RespDto;
import com.yzt.sms.entity.SmsChannel;
import com.yzt.sms.entity.SmsMessage;
import com.yzt.sms.enums.RespCode;
import com.yzt.sms.service.SmsChannelService;
import com.yzt.sms.service.SmsMsgService;
import com.yzt.sms.utils.DesUtils;
import com.yzt.sms.utils.TimeUtils;
import com.yzt.sms.vo.ClearMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

/**
 * 消息处理控制
 *
 * @author yin.ZT
 * @date 2021/07/20 15:12
 **/
@Slf4j
@RestController
@CrossOrigin
public class SmsMessageController {


    final
    SmsChannelService smsChannelService;
    final
    SmsMsgService smsMsgService;

    public SmsMessageController(SmsChannelService smsChannelService, SmsMsgService smsMsgService) {
        this.smsChannelService = smsChannelService;
        this.smsMsgService = smsMsgService;
    }





//    clearMsg
@PostMapping("clearMsg")
public RespDto<?> clearMsg(@Validated @RequestBody ClearMsgVo clearMsgVo){

    SmsMessage smsMessage = new SmsMessage();
    smsMessage.setChannelToken(clearMsgVo.getChannelToken());
    smsMessage.setId(clearMsgVo.getId());
    int i = smsMsgService.deleteSmsMsgList(smsMessage);
    return new RespDto<>(RespCode.SUCCESS,i);
}




    @PostMapping("pushMsg")
    public RespDto<?> pushMsg(
            @RequestParam(name = "token") String channelToken,
            @RequestParam(name = "from") String from,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "timestamp", required = false) Long  timestamp,
            @RequestParam(name = "sign", required = false) String mixSign){

        if (judgeSign(channelToken, timestamp, mixSign)){
            Date smsSendDate = TimeUtils.timeStampToDate(timestamp);
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.setChannelToken(channelToken);
            smsMessage.setFromPlace(from);
            smsMessage.setSign(mixSign);
            smsMessage.setTimestamp(smsSendDate);

            //内容加密处理
            //TODO
            smsMessage.setContent(content);
            log.info("验签成功，正在插入信息 {}",smsMessage);
            String channelEncryptionKey = getSmsChannel(channelToken).getChannelEncryptionKey();
            if (StringUtils.isNotBlank(channelEncryptionKey)){
                DesUtils desUtils = new DesUtils(channelEncryptionKey);
                String encrypt = desUtils.encrypt(content);
                smsMessage.setContent(encrypt);
                log.info("加密成功{}",smsMessage);
            }
            smsMessage = smsMsgService.insertSmsMsg(smsMessage);
            if (null != smsMessage){
                smsMessage.setContent(content);
                log.info("插入成功 {}", smsMessage);
                WebSocketServer.sendInfo(smsMessage);
                return new RespDto<>(RespCode.SUCCESS,smsMessage);
            }
        }

        log.info("验签失败或服务器插入失败，通道为{}，时间戳为{}",channelToken,timestamp);
        return new RespDto<>(RespCode.SERVICE_MISTAKE);
    }



    private boolean judgeSign(String channelToke,Long timestamp,String mixSign){
        log.info("开始验签，通道token-{}，时间戳-{}，合成签名-{}",channelToke,timestamp,mixSign);
        SmsChannel smsChannel1 = getSmsChannel(channelToke);
        if (null != smsChannel1){
            if (StringUtils.isBlank(smsChannel1.getChannelEncryptionKey())){
                log.info("该通道并未设置密钥{}",smsChannel1);
                return true;
            }
            String channelEncryptionKey = smsChannel1.getChannelEncryptionKey();
            //单项加密
            String sign;
            try {
                String stringToSign = timestamp + "\n" + channelEncryptionKey;
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(channelEncryptionKey.getBytes("UTF-8"), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
                sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8);
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
                return false;
            }
            log.info("sign验证中，通道token为 {} ，时间戳为 {} ，通道密码为 {} ，合成签名为 {}，算出的签名为 {}",channelToke,timestamp,channelEncryptionKey,mixSign,sign);
            return sign.equals(mixSign);
        }else {
            return false;
        }

    }

    private SmsChannel getSmsChannel(String channelToke) {
        SmsChannel smsChannel = new SmsChannel();
        smsChannel.setChannelToken(channelToke);
        SmsChannel smsChannel1 = smsChannelService.selectChannel(smsChannel);
        return smsChannel1;
    }


}
