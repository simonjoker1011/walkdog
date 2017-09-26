package prj.serenasimon.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.websocket.Session;

public class ChatCache {

    // <roomID, sessions>
    private static volatile HashMap<String, ArrayList<Session>> chatSessions;
    private static volatile HashMap<Long, Session> onlineUsers;
    static {
        initCache();
    }

    private static void initCache() {
        setChatSessions(new HashMap<String, ArrayList<Session>>());
        setOnlineUsers(new HashMap<Long, Session>());
    }

    public static HashMap<Long, Session> getOnlineUsers() {
        return onlineUsers;
    }

    public static void setOnlineUsers(HashMap<Long, Session> onlineUsers) {
        ChatCache.onlineUsers = onlineUsers;
    }

    public static HashMap<String, ArrayList<Session>> getChatSessions() {
        return chatSessions;
    }

    public static void setChatSessions(HashMap<String, ArrayList<Session>> chatSessions) {
        ChatCache.chatSessions = chatSessions;
    }

    public static void addChatSessions(Session chatSession) {
        if (chatSessions.get(chatSession.getUserProperties().get("room")) == null) {
            chatSessions.put((String) chatSession.getUserProperties().get("room"), new ArrayList<>(Arrays.asList(chatSession)));
        } else {
            chatSessions.get(chatSession.getUserProperties().get("room")).add(chatSession);
        }
    }

    public static void removeChatSessions(Session chatSession) {
        if (chatSessions.get(chatSession.getUserProperties().get("room")) != null) {
            chatSessions.get(chatSession.getUserProperties().get("room")).remove(chatSession);
        }
    }
}
