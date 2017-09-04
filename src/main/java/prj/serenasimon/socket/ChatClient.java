package prj.serenasimon.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatClient {
    private static final Logger logger = LogManager.getLogger(ChatClient.class);
    private static Socket socket;
    private static InetAddress addr;
    private static int port;

    public ChatClient(InetAddress addr, int port) throws IOException {
        setPort(port);
        setAddr(addr);
        createClient();
    }

    public void createClient() throws IOException {
        logger.info("Init client: ");
        socket = new Socket();
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
            socket.close();
        }

    }

    public static void main(String[] args) throws IOException {
        new ChatClient(Inet4Address.getByName("127.0.0.1"), 1024);
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ChatClient.port = port;
    }

    public static InetAddress getAddr() {
        return addr;
    }

    public static void setAddr(InetAddress addr) {
        ChatClient.addr = addr;
    }

}
