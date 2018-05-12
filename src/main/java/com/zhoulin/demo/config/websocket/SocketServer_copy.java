//package com.zhoulin.demo.config.websocket;
//
//import com.zhoulin.demo.bean.UserInfo;
//import com.zhoulin.demo.config.security.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@ServerEndpoint(value = "/socketServer/{token}")
//@Component
//public class SocketServer_copy {
//    private Session session;
//    private static Map<Integer, Session> sessionPool = new HashMap<Integer, Session>();
//    private static Map<String, Integer> sessionIds = new HashMap<String, Integer>();
//
//    @Value("${jwt.secret}")
//    private String secret;
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    /**
//     * 用户连接时触发
//     *
//     * @param session
//     * @param
//     */
//    @OnOpen
//    public void open(Session session,@PathParam(value="token") String token) {
//        this.session = session;
//        UserInfo userInfo = new UserInfo();
////        userInfo = new JwtTokenUtil().parse(token);
//        userInfo = jwtTokenUtil.parse(token);
//        Integer userId = userInfo.getUserId();
//        sessionPool.put(userId, session);
//        sessionIds.put(session.getId(), userId);
//    }
//
//    /**
//     * 收到信息时触发
//     *
//     * @param message
//     */
//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("当前发送人sessionid为" + session.getId() + "发送内容为" + message);
//    }
//
//    /**
//     * 连接关闭触发
//     */
//    @OnClose
//    public void onClose() {
//        sessionPool.remove(sessionIds.get(session.getId()));
//        sessionIds.remove(session.getId());
//    }
//
//    /**
//     * 发生错误时触发
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        error.printStackTrace();
//    }
//
//    /**
//     * 信息发送的方法
//     *
//     * @param message
//     * @param userId
//     */
//    public static void sendMessage(String message, Integer userId) {
//        Session s = sessionPool.get(userId);
//        if (s != null) {
//            try {
//                s.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 获取当前连接数
//     *
//     * @return
//     */
//    public static int getOnlineNum() {
//        return sessionPool.size();
//    }
//
//    /**
//     * 获取在线用户名以逗号隔开
//     *
//     * @return
//     */
//    public static String getOnlineUsers() {
//        StringBuffer users = new StringBuffer();
//        for (String key : sessionIds.keySet()) {
//            users.append(sessionIds.get(key) + ",");
//        }
//        return users.toString();
//    }
//
//    /**
//     * 信息群发
//     *
//     * @param msg
//     */
//    public static void sendAll(String msg) {
//        for (String key : sessionIds.keySet()) {
//            sendMessage(msg, sessionIds.get(key));
//        }
//    }
//}
