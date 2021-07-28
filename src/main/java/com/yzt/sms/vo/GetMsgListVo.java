package com.yzt.sms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取全部信息Vo
 *
 * @author Yin ZT
 * @projectName transms
 * @date 2021/07/27 21:00
 **/
@Data
public class GetMsgListVo implements Serializable {
    private String channelToken;
    private String  userToken;
    private boolean heartBeat;
}
