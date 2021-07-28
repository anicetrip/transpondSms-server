package com.yzt.sms.vo;

import lombok.Data;

/**
 * 清除数据
 *
 * @author Yin ZT
 * @projectName transms
 * @date 2021/07/27 20:41
 **/
@Data
public class ClearMsgVo {
    private String channelToken;
    private Long id;
}
