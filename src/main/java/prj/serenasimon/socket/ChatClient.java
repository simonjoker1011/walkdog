package prj.serenasimon.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.cache.ChatCache;

public class ChatClient {

    private static final Logger logger = LogManager.getLogger(ChatClient.class);

    private UUID serverID;
    private UUID clientID;
    private Socket socket;
    private InetAddress addr;
    private int port;
    private String participant;

    public ChatClient(ChatServer server) throws IOException {

        setClientID(UUID.randomUUID());
        setServerID(server.getServerID());
        setPort(server.getPort());
        setAddr(server.getAddr());

        setSocket(new Socket(addr, port));
        logger.info("connect to server/client: {}/{}", serverID, clientID);
        logger.info("getInetAddress: {}, getLocalAddress: {}, getPort: {}", getSocket().getInetAddress(), getSocket().getLocalAddress(), getSocket().getPort());

        ChatCache.getChatClient().put(getClientID(), this);
    }

    // public void launchConversation() {
    // Conversation conversation = new Conversation(getServerID(), clientSocket);
    // conversation.launch();
    //
    // ChatCache.getConversations().put(conversation.getConversationID(), conversation);
    // }

    public void sendMessage(String content) throws IOException {
        DataOutputStream s = new DataOutputStream(getSocket().getOutputStream());
        logger.info("Message Sent: {}", content);

        s.writeUTF(content);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }

    public UUID getServerID() {
        return serverID;
    }

    public void setServerID(UUID serverID) {
        this.serverID = serverID;
    }

    public static void main(String[] args) throws IOException {
        // new ChatClient(UUID.fromString("8f427ae6-9e0b-4fc2-80d7-de864e25b9ee"), Inet4Address.getByName("127.0.0.1"), 1024);
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
