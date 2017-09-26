package prj.serenasimon.websocket;

import java.io.IOException;
import java.util.ArrayList;

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

@ServerEndpoint(value = "/chat/user/{userid}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class P2pEndpoint {
    private static final Logger logger = LogManager.getLogger(P2pEndpoint.class);

    @OnOpen
    public void open(Session session, @PathParam("userid") final Long userid) {
        logger.info("ws opened; session: {}, user: {}", session.getId(), userid);

        session.getUserProperties().put("userID", userid);
        session.getUserProperties().put("reveivers", new ArrayList<Long>());
        ChatCache.getOnlineUsers().put(userid, session);
    }

    @OnClose
    public void close(final Session session) {
        logger.info("ws closed; session: {}, user: {}", session.getId(), session.getUserProperties().get("userID"));
        ChatCache.getOnlineUsers().remove(session.getUserProperties().get("userID"));
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        ArrayList<Long> receivers = (ArrayList) ChatCache.getOnlineUsers().get(session.getUserProperties().get("userID"))
            .getUserProperties().get("reveivers");

        for (Long id : receivers) {
            Session client = ChatCache.getOnlineUsers().get(id);
            if (client.isOpen() && !client.equals(session)) {
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
