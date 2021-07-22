package com.yzt.sms;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 计算发送内容的加密token
 *
 * @author yin.ZT
 * @date 2021/07/22 13:37
 **/
public class mixtokenCaculate {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // write your code here
        Long timestamp = 1626769778204L;
        String secret = "fake_data";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8);
        System.out.println(sign);

    }
}
