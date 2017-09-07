package prj.serenasimon.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.cache.ChatCache;

public class ChatServer implements Runnable {

    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private static final int SERVER_TIME_OUT = 10 * 60 * 1000; // in milliseconds

    private UUID serverID;
    private int port;
    private InetAddress addr;
    private int socketTimeout;
    private ServerSocket serverSocket;
    private Set<UUID> clientIDs;
    private Set<String> participants;

    public ChatServer() {
        try {
            serverSocket = create();

            setServerID(UUID.randomUUID());
            setPort(serverSocket.getLocalPort());
            setAddr(serverSocket.getInetAddress());
            setSocketTimeout(serverSocket.getSoTimeout());
            setParticipants(new HashSet<String>());
            setClientIDs(new HashSet<UUID>());
            logger.info("Init Server Socket; ID:{}, Addr: {}, Port: {}, Timeout: {}", getServerID(), serverSocket.getInetAddress(), serverSocket.getLocalPort(),
                serverSocket.getSoTimeout());

            ChatCache.getChatServer().put(getServerID(), this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void launch() {
        logger.info("ChatServer launch...");
        Thread thread = new Thread(this);
        thread.start();
    }

    public ServerSocket create() throws IOException {
        for (int i = 1; i <= 65535; i++) {
            try {
                ServerSocket socket = new ServerSocket(i);
                socket.setSoTimeout(SERVER_TIME_OUT);
                return socket;
            } catch (IOException ex) {
                continue; // try next port
            }
        }
        // if the program gets here, no port in the range was found
        throw new IOException("no free port found");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Current Client: {}.{}", clientSocket.getLocalAddress(), clientSocket.getLocalPort());

                Conversation conversation = new Conversation(getServerID(), clientSocket, participants);
                conversation.launch();

                ChatCache.getConversations().put(getServerID(), conversation);
            } catch (SocketTimeoutException e) {
                // e.printStackTrace();
                logger.warn("Server Socket Timeout reached...");
                // break;
                if (getParticipants().size() < 1) {
                    logger.info("No participant in room, terminated...");
                    ChatCache.getChatServer().remove(getServerID());
                    break;
                } else {
                    try {
                        logger.info("Extends Timeout: {}s", SERVER_TIME_OUT / 1000);
                        serverSocket.setSoTimeout(SERVER_TIME_OUT);
                        continue;
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddr() {
        return this.addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public UUID getServerID() {
        return serverID;
    }

    public void setServerID(UUID serverID) {
        this.serverID = serverID;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new ChatServer();
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }

    public Set<UUID> getClientIDs() {
        return clientIDs;
    }

    public void setClientIDs(Set<UUID> clientIDs) {
        this.clientIDs = clientIDs;
    }

}
