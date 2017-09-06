package prj.serenasimon.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import prj.serenasimon.socket.ChatClient;
import prj.serenasimon.socket.ChatServer;
import prj.serenasimon.socket.Conversation;

public class ChatCache {

    private static volatile Map<UUID, ChatServer> chatServer;
    private static volatile Map<UUID, ChatClient> chatClient;
    private static volatile Map<UUID, Conversation> conversations;

    static {
        initCache();
    }

    private static void initCache() {
        setChatServer(new HashMap<UUID, ChatServer>());
        setChatClient(new HashMap<UUID, ChatClient>());
        setConversations(new HashMap<UUID, Conversation>());
    }

    public static Map<UUID, ChatServer> getChatServer() {
        return chatServer;
    }

    public static void setChatServer(Map<UUID, ChatServer> chatServer) {
        ChatCache.chatServer = chatServer;
    }

    public static Map<UUID, ChatClient> getChatClient() {
        return chatClient;
    }

    public static void setChatClient(Map<UUID, ChatClient> chatClient) {
        ChatCache.chatClient = chatClient;
    }

    public static Map<UUID, Conversation> getConversations() {
        return conversations;
    }

    public static void setConversations(Map<UUID, Conversation> conversations) {
        ChatCache.conversations = conversations;
    }

}
