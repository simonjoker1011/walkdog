package prj.serenasimon.websocket;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.cache.ChatCache;
import prj.serenasimon.chat.ChatMessageDecoder;
import prj.serenasimon.chat.ChatMessageEncoder;
import prj.serenasimon.datas.ChatMessage;

@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

    @OnOpen
    public void open(Session session, @PathParam("room") final String room) {
        logger.info("ws opened; room: {}", room);

        session.getUserProperties().put("room", room);
        ChatCache.addChatSessions(session);
    }

    @OnClose
    public void close(final Session session) {
        logger.info("ws closed; room: {}", session.getUserProperties().get("room"));
        ChatCache.removeChatSessions(session);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        for (Session client : ChatCache.getChatSessions().get(session.getUserProperties().get("room"))) {
            if (!client.equals(session)) {
                try {
                    client.getBasicRemote().sendObject(chatMessage);
                } catch (IOException | EncodeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
