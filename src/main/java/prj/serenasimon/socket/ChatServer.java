package prj.serenasimon.socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatServer implements Runnable {

    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private int port;
    private InetAddress addr;
    private int socketTimeout;

    public ChatServer() {
        this.run();
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatServer();
    }

    public static ServerSocket create() throws IOException {
        for (int i = 1; i <= 65535; i++) {
            try {
                ServerSocket socket = new ServerSocket(i);
                socket.setSoTimeout(30 * 1000);
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
            ServerSocket serverSocket = create();
            setPort(serverSocket.getLocalPort());
            setAddr(serverSocket.getInetAddress());
            setSocketTimeout(serverSocket.getSoTimeout());

            logger.info("Init Server Socket, Addr: {}, Port: {}, Timeout: {}", serverSocket.getInetAddress(), serverSocket.getLocalPort(), serverSocket.getSoTimeout());

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
}
