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

    private static UUID serverID;
    private static UUID clientID;
    private static Socket socket;
    private static InetAddress addr;
    private static int port;
    private static String participant;

    public ChatClient(UUID serverID, InetAddress addr, int port) throws IOException {
        logger.info("Init client...");

        setServerID(serverID);
        setClientID(UUID.randomUUID());
        setPort(port);
        setAddr(addr);

        socket = new Socket(addr, port);
        logger.info("connect to server/client: {}/{}", serverID, clientID);
        logger.info("getInetAddress: {}, getLocalAddress: {}, getPort: {}", socket.getInetAddress(), socket.getLocalAddress(), socket.getPort());
        // createClient();

        ChatCache.getChatClient().put(getClientID(), this);
    }

    public void createClient() throws IOException {
        logger.info("Init client: ");
        socket = null;
        try {
            socket = new Socket(addr, port);
            logger.info("getInetAddress: {}, getLocalAddress: {}, getPort: {}", socket.getInetAddress(), socket.getLocalAddress(), socket.getPort());
            DataOutputStream s = new DataOutputStream(socket.getOutputStream());
            for (int i = 0; i < 10; i++) {
                String msg = Double.toString(Math.random());
                logger.info("Message Sent: {}", msg);
                s.writeUTF(msg);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

    }

    public void sendMessage(String content) throws IOException {
        DataOutputStream s = new DataOutputStream(socket.getOutputStream());
        logger.info("Message Sent: {}", content);

        s.writeUTF(content);

    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        ChatClient.port = port;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        ChatClient.addr = addr;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        ChatClient.clientID = clientID;
    }

    public UUID getServerID() {
        return serverID;
    }

    public void setServerID(UUID serverID) {
        ChatClient.serverID = serverID;
    }

    public static void main(String[] args) throws IOException {
        // new ChatClient(UUID.fromString("8f427ae6-9e0b-4fc2-80d7-de864e25b9ee"), Inet4Address.getByName("127.0.0.1"), 1024);
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        ChatClient.participant = participant;
    }
}
