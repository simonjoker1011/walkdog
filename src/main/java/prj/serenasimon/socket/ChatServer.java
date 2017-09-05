package prj.serenasimon.socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.cache.ChatCache;

public class ChatServer implements Runnable {

    private static final Logger logger = LogManager.getLogger(ChatServer.class);

    private static UUID serverID;
    private int port;
    private InetAddress addr;
    private int socketTimeout;
    private ServerSocket serverSocket;
    private Set<String> participants;

    public ChatServer() {
        try {
            serverSocket = create();

            setServerID(UUID.randomUUID());
            setPort(serverSocket.getLocalPort());
            setAddr(serverSocket.getInetAddress());
            setSocketTimeout(serverSocket.getSoTimeout());
            setParticipants(new HashSet<String>());
            logger.info("Init Server Socket; ID:{}, Addr: {}, Port: {}, Timeout: {}", getServerID(), serverSocket.getInetAddress(), serverSocket.getLocalPort(),
                serverSocket.getSoTimeout());

            ChatCache.getChatServer().put(getServerID(), this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    public static ServerSocket create() throws IOException {
        for (int i = 1; i <= 65535; i++) {
            try {
                ServerSocket socket = new ServerSocket(i);
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
        try {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Current Client: {}.{}", clientSocket.getLocalAddress(), clientSocket.getLocalPort());
                while (true) {
                    try {
                        DataInputStream s = new DataInputStream(clientSocket.getInputStream());
                        logger.info("Msg: {}", new String(s.readUTF()));
                    } catch (EOFException e) {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

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
        ChatServer.serverID = serverID;
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

}
