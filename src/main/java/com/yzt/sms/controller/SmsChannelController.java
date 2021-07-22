package com.yzt.sms.controller;

import com.yzt.sms.dto.AddChannelDto;
import com.yzt.sms.dto.RespDto;
import com.yzt.sms.entity.SmsChannel;
import com.yzt.sms.entity.SmsUserEntity;
import com.yzt.sms.enums.RespCode;
import com.yzt.sms.service.SmsChannelService;
import com.yzt.sms.service.SmsUserService;
import com.yzt.sms.utils.HttpServletUtils;
import com.yzt.sms.utils.TokenGeneration;
import com.yzt.sms.vo.AddChannelVo;
import com.yzt.sms.vo.DelChannelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;



/**
 * 通道相关controller
 *
 * @author 17566
 * @projectName sms
 * @date 2021/07/18 15:20
 **/
@Slf4j
@RestController
public class SmsChannelController {

    SmsUserService smsUserService;
    SmsChannelService smsChannelService;
    @Autowired
    public SmsChannelController(SmsUserService smsUserService,SmsChannelService smsChannelService){
        this.smsUserService = smsUserService;
        this.smsChannelService =smsChannelService;
    }


    /**
     * 获得通道列表
     * @author yin.zhengtao
     * @date 15:25 2021/7/22
     * @param
     * @return RespDto<?>
     **/
    @GetMapping("getChannelList")
    public RespDto<?> getChannelList(){
        SmsUserEntity smsUserEntity = judgeSmsUserExit();
        SmsChannel smsChannel = new SmsChannel();
        smsChannel.setUserId(smsUserEntity.getUserId());
        log.info("查询该用户的channel {}",smsUserEntity);
        List<SmsChannel> smsChannelList = smsChannelService.selectChannelList(smsChannel);
        return new RespDto<>(RespCode.SUCCESS,smsChannelList);
    }


    /**
     * 删除通道
     * @author yin.ZT
     * @date 17:16 2021/7/19
     * @param delChannelVo 删除通道Vo
     * @return RespDto<?>
     **/
    @PostMapping("delChannel")
    public RespDto<?> delChannel(@Validated @RequestBody DelChannelVo delChannelVo){
        Integer id = delChannelVo.getId();
        SmsUserEntity smsUserEntity = judgeSmsUserExit();
        if (null == smsUserEntity){
            return new RespDto<>(RespCode.TOKEN_NONEXISTENT);
        }
        if (null == id){
            return new RespDto<>(RespCode.SERVICE_MISTAKE);
        }
        SmsChannel smsChannel = new SmsChannel();
        smsChannel.setChannelId(id);
        smsChannel.setUserId(smsUserEntity.getUserId());
        //查询token是否存在该对应关系
        SmsChannel judgeSmsChannelExit =  smsChannelService.selectChannel(smsChannel);
        if (null != judgeSmsChannelExit){
            log.info("用户正在删除通道{}",judgeSmsChannelExit);
            int i = smsChannelService.deleteByChannelId(smsChannel);
            if (i != 0){
                log.info("用户删除通道成功{}",judgeSmsChannelExit);
                return new RespDto<>(RespCode.SUCCESS);
            }else {
                log.info("删除失败，服务器错误 {}",smsChannel);
                return new RespDto<>(RespCode.SERVICE_MISTAKE);
            }
        }
        return new RespDto<>(RespCode.TOKEN_NONEXISTENT);

    }




    /**
     *
     * @author yin.ZT
     * @date 12:38 2021/7/19
     * @param addChannelVo  新增通道vo
     * @return RespDto<AddChannelDto>
     **/
    @PostMapping("addChannel")
    public RespDto<AddChannelDto> addChannel(@Validated @RequestBody AddChannelVo addChannelVo){
        SmsUserEntity smsUserEntity = judgeSmsUserExit();
        if (null == smsUserEntity){
            log.info("token不存在 {}",addChannelVo);
            return new RespDto<>(RespCode.TOKEN_NONEXISTENT);
    }
        log.info("用户token为{}，增加的通道信息为：{}",smsUserEntity.getToken(),addChannelVo);
        SmsChannel smsChannel = new SmsChannel();
        BeanUtils.copyProperties(addChannelVo,smsChannel);
        smsChannel.setUserId(smsUserEntity.getUserId());
        log.info("该用户正常新增通道 {}",smsUserEntity);
        smsChannel.setUserId(smsUserEntity.getUserId());
        smsChannel.setChannelToken(TokenGeneration.tokenGeneration(24));
        smsChannel = smsChannelService.insertSmsChannel(smsChannel);
        if (null != smsChannel){
            log.info("插入成功 {}",smsChannel);
            final AddChannelDto addChannelDto = new AddChannelDto();
            BeanUtils.copyProperties(smsChannel,addChannelDto);
            return new RespDto<>(RespCode.SUCCESS,addChannelDto);
        }else {
            log.info("插入错误，服务器错误 {}",addChannelVo);
            return new RespDto<>(RespCode.SERVICE_MISTAKE);
        }

    }




    protected SmsUserEntity judgeSmsUserExit(){
        final String token = HttpServletUtils.getRequestHeader("token");
        final SmsUserEntity smsUserEntity = new SmsUserEntity();
        smsUserEntity.setToken(token);
        log.info("查询token为{}的用户。",token);
        final SmsUserEntity smsUserEntity1 = smsUserService.selectSmsUser(smsUserEntity);
        log.info("查询到的token用户为{}",smsUserEntity1);
        return smsUserEntity1;
    }


}
