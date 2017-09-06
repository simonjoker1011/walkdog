package prj.serenasimon.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Conversation implements Runnable {

    private static final Logger logger = LogManager.getLogger(Conversation.class);
    private static final int CLIENT_TIME_OUT = 10 * 1000; // in milliseconds

    private UUID serverID;
    private UUID clientID;
    private Socket clientSocket;

    public Conversation(UUID serverID, Socket clientSocket) throws SocketException {
        setServerID(serverID);
        // setClientID(clientID);
        clientSocket.setSoTimeout(CLIENT_TIME_OUT);
        setClientSocket(clientSocket);
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
                e.printStackTrace();
                break;
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

}
