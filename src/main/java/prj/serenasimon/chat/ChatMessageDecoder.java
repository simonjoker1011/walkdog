package prj.serenasimon.chat;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.datas.ChatMessage;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
    private static final Logger logger = LogManager.getLogger(ChatMessageDecoder.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(final EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public ChatMessage decode(final String textMessage) throws DecodeException {
        ChatMessage chatMessage = new ChatMessage();
        JsonObject obj = Json.createReader(new StringReader(textMessage))
            .readObject();
        chatMessage.setMessage(obj.getString("message"));
        chatMessage.setSenderid(obj.getString("senderid"));
        chatMessage.setReveiverid(obj.getString("reveiverid"));
        chatMessage.setAction(obj.getString("action"));
        chatMessage.setReceived(new Date());
        return chatMessage;
    }

    @Override
    public boolean willDecode(final String arg0) {
        // TODO Auto-generated method stub
        return true;
    }

}
