package com.yzt.sms.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;


/**
 * 获取http相关工具类
 *
 * @author Yin ZT
 * @projectName sms
 * @date 2021/07/18 21:46
 **/
public class HttpServletUtils {

    /**
     * 获取请求头信息
     * @author YIN ZT
     * @date 21:51 2021/7/18
     * @param name  请求头名称
     * @return String
    **/
    public static String getRequestHeader(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return request.getHeader(name);
    }

    /**
     * 获取cookie
     * @author YIN ZT
     * @date 21:52 2021/7/18
     * @param name  cookie字段名
     * @return String
    **/
    public static String getCookie(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Cookie[] cookies = request.getCookies();
        if(null == cookies || cookies.length == 0) {
            return null;
        }
        for(Cookie c : cookies) {
            if(c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return null;
    }

    /**
     * 获取httpsession
     * @author YIN ZT
     * @date 21:53 2021/7/18
     * @return HttpSession
    **/
    public static HttpSession getHttpSession() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return request.getSession();
    }

    /**
     * 获取ip地址
     * @author YIN ZT
     * @date 21:54 2021/7/18
     * @param request  请求内容
     * @return String
    **/
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
