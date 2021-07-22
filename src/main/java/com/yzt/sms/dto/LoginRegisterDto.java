package com.yzt.sms.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 11:16
 * @ProjectName transms
 * @Discription TODO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRegisterDto {
    /**
     * 主键
     */
    private Integer userId;

    private String userName;

    private String token;

    private String secretKey;
}
