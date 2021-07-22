package com.yzt.sms.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author Yin ZT
 * @Data 2021/7/16 12:55
 * @ProjectName transms
 * @Discription TODO
 **/
public class TokenGeneration {
    public static String tokenGeneration(int len){
        return RandomStringUtils.random(len, "abcdefghijklmnopqrstuvwxyz1234567890");
    }




}
