package com.yzt.sms.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yzt.sms.entity.SmsChannel;
import com.yzt.sms.entity.SmsMessage;
import com.yzt.sms.service.SmsChannelService;
import com.yzt.sms.service.SmsMsgService;
import com.yzt.sms.utils.DesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author yin.ZT
 */ //https://zhengkai.blog.csdn.net/article/details/80275084?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-8.base&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-8.base
@ServerEndpoint("/getMsgList/{channelToken}")
@Component
@Slf4j
public class WebSocketServer {

    static SmsMsgService smsMsgService;

    static SmsChannelService smsChannelService;

    @Autowired
    public void setSmsMsgService(SmsMsgService smsMsgService,SmsChannelService smsChannelService){
        WebSocketServer.smsMsgService = smsMsgService;
        WebSocketServer.smsChannelService = smsChannelService;
    }


    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收channelId*/
    private String channelToken ="";
    private String channelEncryptionKey = "";


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("channelToken") String channelToken) {
        this.session = session;
        this.channelToken =channelToken;
        log.info("获取的通道token为：{}",channelToken);
        this.channelEncryptionKey = getSmsChannel(channelToken).getChannelEncryptionKey();
        //处理通道
        if(webSocketMap.containsKey(channelToken)){
            webSocketMap.remove(channelToken);
            webSocketMap.put(channelToken,this);
            //加入set中
        }else{
            webSocketMap.put(channelToken,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("通道连接:"+channelToken+",当前在线通道数为:" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("通道:"+channelToken+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(channelToken)){
            webSocketMap.remove(channelToken);
            //从set中删除
            subOnlineCount();
        }
        log.info("通道退出:"+ channelToken +",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param channelToken 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String channelToken, Session session) throws EncodeException, IOException {
        log.info("通道消息:"+ this.channelToken +",入参消息通道:"+channelToken);

        //发送消息
        if (this.channelToken.equals(channelToken)){
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.setChannelToken(channelToken);
            List<SmsMessage> selectSmsMsgList= smsMsgService.selectSmsMsgList(smsMessage);
            if (StringUtils.isNotBlank(channelEncryptionKey)){
                DesUtils desUtils = new DesUtils(channelEncryptionKey);
                for (SmsMessage message : selectSmsMsgList) {
                    String decrypt = desUtils.decrypt(message.getContent());
                    message.setContent(decrypt);
                }
            }
            if (null != selectSmsMsgList){
                String jsonStr = JSONObject.toJSONString( selectSmsMsgList );
                webSocketMap.get(channelToken).sendMessage(jsonStr);
            }
        }else {
            log.info("通道不存在 {}",channelToken);
        }

    }

    /**
     *  错误信息
     * @param session session
     * @param error error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.channelToken +",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }



    /**
     * 发送自定义消息
     * */
    public static void sendInfo(SmsMessage smsMessage) {
        if (webSocketMap.containsKey(smsMessage.getChannelToken())){
            log.info("同步推送至前端{}",smsMessage);
            try{
                String jsonStr = JSONObject.toJSONString( smsMessage );
                webSocketMap.get(smsMessage.getChannelToken()).sendMessage(jsonStr);
            } catch (IOException e) {
                log.info("同步推至前端异常 {}",smsMessage);
            }

        }
    }





//    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
//        log.info("发送消息到:"+userId+"，报文:"+message);
//        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
//            webSocketMap.get(userId).sendMessage(message);
//        }else{
//            log.error("用户"+userId+",不在线！");
//        }
//    }


    private SmsChannel getSmsChannel(String channelToke) {
        SmsChannel smsChannel = new SmsChannel();
        smsChannel.setChannelToken(channelToke);
        SmsChannel smsChannel1 = smsChannelService.selectChannel(smsChannel);
        return smsChannel1;
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
