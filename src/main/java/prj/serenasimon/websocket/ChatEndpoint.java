package prj.serenasimon.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.chat.ChatMessageDecoder;
import prj.serenasimon.chat.ChatMessageEncoder;
import prj.serenasimon.datas.ChatMessage;

@ServerEndpoint(value = "/chat", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(final Session session) {
        logger.info("ws opened; session: {}", session.getId());
        clients.add(session);
    }

    @OnClose
    public void close(final Session session) {
        logger.info("ws closed; session: {}", session.getId());
        clients.remove(session);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        synchronized (clients) {
            for (Session client : clients) {
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
}
