package com.zhoulin.demo.config.websocket;

import com.zhoulin.demo.utils.ReckonUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WsHandler extends TextWebSocketHandler
{

    @Autowired
    private ReckonUserGroup reckonUserGroup;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println("close....");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        super.afterConnectionEstablished(session);

        System.out.println("建立新的会话");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        System.out.println(message.getPayload());
        TextMessage msg=new TextMessage(message.getPayload());
        session.sendMessage(msg);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception
    {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception
    {
        super.handleTransportError(session, exception);
    }

}
