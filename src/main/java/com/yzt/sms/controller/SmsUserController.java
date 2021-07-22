package com.yzt.sms.controller;

import antlr.Token;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.common.Constants;
import com.yzt.sms.dto.LoginRegisterDto;
import com.yzt.sms.dto.ResetPassWordBySecretKeyDto;
import com.yzt.sms.dto.ResetPassWordByTokenDto;
import com.yzt.sms.dto.RespDto;
import com.yzt.sms.entity.SmsUserEntity;
import com.yzt.sms.enums.RespCode;
import com.yzt.sms.service.SmsUserService;
import com.yzt.sms.utils.TokenGeneration;
import com.yzt.sms.vo.DelAccountVo;
import com.yzt.sms.vo.LoginRegisterVo;
import com.yzt.sms.vo.ResetPassWordBySecretKeyVo;
import com.yzt.sms.vo.ResetPassWordByTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yin ZT
 * @data 2021/7/16 9:52
 * @projectName transms
 * 用户系统管理
 **/
@Slf4j
@RestController
public class SmsUserController {

    SmsUserService smsUserService;

    @Autowired
    public SmsUserController(SmsUserService smsUserService){
        this.smsUserService =smsUserService;
    }







    @PostMapping("resetPassWordByToken")
    public RespDto<ResetPassWordByTokenDto> resetPassWordByToken(@Validated @RequestBody ResetPassWordByTokenVo resetPassWordByTokenVo){
        final SmsUserEntity smsUserEntity = new SmsUserEntity();
        smsUserEntity.setToken(resetPassWordByTokenVo.getToken());
        final SmsUserEntity smsUserJudgeExist = smsUserService.selectSmsUser(smsUserEntity);
        log.info("根据token查询密码，查询到的用户信息 {}",smsUserJudgeExist);
        if (null != smsUserJudgeExist){
            smsUserJudgeExist.setToken(TokenGeneration.tokenGeneration(16));
            smsUserJudgeExist.setPassword(resetPassWordByTokenVo.getPassword());
            log.info("用户更新信息{}",smsUserJudgeExist);
            final int i = smsUserService.updateSmsUser(smsUserJudgeExist);
            if (0 != i){
                final ResetPassWordByTokenDto resetPassWordByTokenDto = new ResetPassWordByTokenDto();
                BeanUtils.copyProperties(smsUserJudgeExist,resetPassWordByTokenDto);
                log.info("用户修改密码,新的token和密码为{}，完整信息为：{}",resetPassWordByTokenDto,smsUserJudgeExist);
                return new RespDto<>(RespCode.SUCCESS,resetPassWordByTokenDto);
            }else {
                log.info("用户修改密码失败");
                return new RespDto<>(RespCode.SERVICE_MISTAKE);
            }
        }else {
            return new RespDto<>(RespCode.SERVICE_MISTAKE);
        }

    }




        /**
         * 通过密钥找回密码
         * @author 17566
         * @date 10:06 2021/7/18
         * @param resetPassWordBySecretKeyVo  Vo
         * @return RespDto<ResetPassWordBySecretKeyDto> 如果修改成功会返回新的token和密钥
        **/
    @PostMapping("resetPassWordBySecretKey")
    public RespDto<ResetPassWordBySecretKeyDto> resetPassWordBySecretKey(@Validated @RequestBody ResetPassWordBySecretKeyVo resetPassWordBySecretKeyVo){
        if (StringUtils.isBlank(resetPassWordBySecretKeyVo.getSecretKey())){
            log.info("密钥为空");
            return new RespDto<>(RespCode.WRONG_SECRET_KEY);
        }
        final SmsUserEntity smsUserEntity = new SmsUserEntity();
        smsUserEntity.setSecretKey(resetPassWordBySecretKeyVo.getSecretKey());
        smsUserEntity.setLoginEmail(StringUtils.isNotBlank(resetPassWordBySecretKeyVo.getLoginEmail())? resetPassWordBySecretKeyVo.getLoginEmail():null);
        smsUserEntity.setLoginPhone(null != resetPassWordBySecretKeyVo.getLoginPhone()?resetPassWordBySecretKeyVo.getLoginPhone():null);
        final SmsUserEntity smsUserJudgeExist = smsUserService.selectSmsUser(smsUserEntity);
        log.info("尝试找回密码 {}", smsUserJudgeExist);
        if (smsUserJudgeExist != null){
            smsUserJudgeExist.setPassword(resetPassWordBySecretKeyVo.getPassword());
            smsUserJudgeExist.setSecretKey(TokenGeneration.tokenGeneration(16));
            smsUserJudgeExist.setToken(TokenGeneration.tokenGeneration(16));
            log.info("校验成功，开始修改密码 {}", smsUserJudgeExist);
            final int i = smsUserService.updateSmsUser(smsUserJudgeExist);
            if (0 != i){
                final ResetPassWordBySecretKeyDto resetPassWordBySecretKeyDto = new ResetPassWordBySecretKeyDto();
                BeanUtils.copyProperties(smsUserJudgeExist,resetPassWordBySecretKeyDto);
                log.info("修改成功,新密码、密钥和密码为：{}，完整信息：{}",resetPassWordBySecretKeyDto,smsUserJudgeExist);
                return new RespDto<>(RespCode.SUCCESS,resetPassWordBySecretKeyDto);
            }else {
                log.info("修改失败");
                return new RespDto<>(RespCode.SERVICE_MISTAKE);
            }
        }else {
            log.info("密钥错误{}",resetPassWordBySecretKeyVo);
            return new RespDto<>(RespCode.WRONG_SECRET_KEY);
        }
    }






    /**
     * 删除账号
     * @author 17566
     * @date 23:05 2021/7/17
     * @param delAccountVo  vo
     * @return RespDto<?> 返回
    **/
    @PostMapping("delAccount")
    public RespDto<?> delAccount(@Validated @RequestBody DelAccountVo delAccountVo){
        log.info(delAccountVo.getToken()+ "进行删除操作");
        int i = smsUserService.deleteSmsUser(delAccountVo.getToken());
        if (i != 0){
            log.info(delAccountVo.getToken()+ "删除成功");
            return new RespDto<>(RespCode.DELETE_ACCOUNT_SUCCESS);
        }else {
            log.info(delAccountVo.getToken()+ "删除失败，请练习管理员");
            return new RespDto<>(RespCode.SERVICE_MISTAKE);
        }
    }


    /**
     *  登录/注册
     * @author yin.ZT
     * @date 15:17 2021/7/16
     * @param loginRegisterVo vo
     * @return RespDto<LoginRegisterDto>
     **/
    @PostMapping("loginRegister")
    public RespDto<LoginRegisterDto> loginRegister(@Validated @RequestBody LoginRegisterVo loginRegisterVo){
        SmsUserEntity smsUserEntity = new SmsUserEntity();
        smsUserEntity.setLoginEmail(loginRegisterVo.getLoginEmail());
        smsUserEntity.setLoginPhone(loginRegisterVo.getLoginPhone());
        SmsUserEntity smsUserJudgeExist = smsUserService.selectSmsUser(smsUserEntity);
        if (null == smsUserJudgeExist){
            //注册操作
            LoginRegisterDto loginRegisterDto = smsUserRegister(loginRegisterVo);
            return new RespDto<>(RespCode.LOGIN_REGISTER_SUCCESS, loginRegisterDto);
        }else{
            //登录操作
            LoginRegisterDto loginRegisterDto = smsUserLogin(loginRegisterVo, smsUserJudgeExist);
            if (Constants.WRONG_PASSWORD.equals(loginRegisterDto.getToken())){
                return new RespDto<>(RespCode.LOGIN_REGISTER_FAILED);
            }else {
                return new RespDto<>(RespCode.LOGIN_REGISTER_SUCCESS, loginRegisterDto);
            }
        }
    }

    /**
     *
     * @author yin.ZT
     * @date 15:17 2021/7/16
     * @param loginRegisterVo vo
     * @return LoginRegisterDto
     **/
    private LoginRegisterDto smsUserRegister(LoginRegisterVo loginRegisterVo){
        SmsUserEntity smsUserEntity = new SmsUserEntity();
        BeanUtils.copyProperties(loginRegisterVo,smsUserEntity);
        String s = TokenGeneration.tokenGeneration(16);
        smsUserEntity.setToken(s);
        smsUserEntity.setSecretKey(TokenGeneration.tokenGeneration(16));
        smsUserEntity.setIsDeleted(0);
        //插入
        log.info("用户注册 {}", smsUserEntity);
        SmsUserEntity smsUserFromDb = smsUserService.insertSmsUser(smsUserEntity);
        LoginRegisterDto loginRegisterDto = new LoginRegisterDto();
        BeanUtils.copyProperties(smsUserFromDb,loginRegisterDto);
        String userName;
        if (StringUtils.isNotBlank(smsUserFromDb.getLoginEmail())){
            userName = smsUserFromDb.getLoginEmail();
        }else {
            userName = smsUserFromDb.getLoginPhone().toString();
        }
        loginRegisterDto.setUserName(userName);
        loginRegisterDto.setSecretKey(smsUserFromDb.getSecretKey());
        return loginRegisterDto;
        
    }

    /**
     *
     * @author yin.ZT
     * @date 15:16 2021/7/16
     * @param loginRegisterVo vo
     * @param smsUserJudgeExist 数据库根据手机号/邮箱查出的数据
     * @return LoginRegisterDto 如果值为"-1"的情况，则说明密码错误
     **/
    private LoginRegisterDto smsUserLogin(LoginRegisterVo loginRegisterVo, SmsUserEntity smsUserJudgeExist){
        LoginRegisterDto loginRegisterDto = new LoginRegisterDto();
        if (loginRegisterVo.getPassword().equals(smsUserJudgeExist.getPassword())){
            log.info("用户登录成功 {}", smsUserJudgeExist);

            BeanUtils.copyProperties(smsUserJudgeExist,loginRegisterDto);
            //处理name
            if(StringUtils.isNotBlank(smsUserJudgeExist.getLoginEmail())){
                loginRegisterDto.setUserName(smsUserJudgeExist.getLoginEmail());
            }else {
                loginRegisterDto.setUserName(smsUserJudgeExist.getLoginPhone().toString());
            }
        }else {
            log.info("用户登录异常,使用的登录信息为 {}",loginRegisterVo);
            loginRegisterDto.setToken(Constants.WRONG_PASSWORD);
        }
        return loginRegisterDto;
    }
}
