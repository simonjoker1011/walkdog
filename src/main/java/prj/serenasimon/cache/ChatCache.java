package prj.serenasimon.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

public class ChatCache {

    private static volatile Map<String, List<Session>> chatSessions;

    static {
        initCache();
    }

    private static void initCache() {
        setChatSessions(new HashMap<String, List<Session>>());
    }

    public static Map<String, List<Session>> getChatSessions() {
        return chatSessions;
    }

    public static void setChatSessions(Map<String, List<Session>> chatSessions) {
        ChatCache.chatSessions = chatSessions;
    }

    public static void addChatSessions(Session chatSession) {
        if (chatSessions.get(chatSession.getUserProperties().get("room")) == null) {
            chatSessions.put((String) chatSession.getUserProperties().get("room"), Arrays.asList(chatSession));
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
