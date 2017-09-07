package prj.serenasimon.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.cache.ChatCache;

public class Conversation implements Runnable {

    private static final Logger logger = LogManager.getLogger(Conversation.class);
    private static final int CLIENT_TIME_OUT = 10 * 60 * 1000; // in milliseconds

    private UUID serverID;
    private Set<String> participants;
    private Socket clientSocket;

    public Conversation(UUID serverID, Socket clientSocket, Set<String> participants) throws SocketException {
        setServerID(serverID);
        clientSocket.setSoTimeout(CLIENT_TIME_OUT);
        setClientSocket(clientSocket);
        setParticipants(participants);
    }

    public void launch() {
        logger.info("Conversation launch...");
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream s = new DataInputStream(getClientSocket().getInputStream());
                logger.info("Msg: {}", new String(s.readUTF()));
            } catch (SocketTimeoutException e) {
                logger.warn("Client Socket Timeout reached...");
                if (getParticipants().size() < 1) {
                    logger.info("No participant in room, terminated...");
                    ChatCache.getConversations().remove(getServerID());
                    break;
                } else {
                    try {
                        logger.info("Extends Timeout: {}s", CLIENT_TIME_OUT / 1000);
                        clientSocket.setSoTimeout(CLIENT_TIME_OUT);
                        continue;
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                        break;
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public UUID getServerID() {
        return serverID;
    }

    public void setServerID(UUID serverID) {
        this.serverID = serverID;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }

}
