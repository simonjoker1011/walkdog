package prj.serenasimon.websocket;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.chat.ChatMessageDecoder;
import prj.serenasimon.chat.ChatMessageEncoder;
import prj.serenasimon.datas.ChatMessage;

@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        logger.info("session openend and bound to room: " + room);
        session.getUserProperties().put("room", room);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()
                    && room.equals(s.getUserProperties().get("room"))) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            logger.warn("onMessage failed", e);
        }
    }
}
