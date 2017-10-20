package prj.serenasimon.chat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import prj.serenasimon.datas.ChatMessage;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    private static final Logger logger = LogManager.getLogger(ChatMessageEncoder.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(final EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(final ChatMessage chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
            .add("message", chatMessage.getMessage())
            .add("action", chatMessage.getAction().toString())
            .add("senderid", chatMessage.getSenderid())
            .add("reveiverid", chatMessage.getReveiverid())
            .add("received", chatMessage.getReceived().toString())
            .build().toString();
    }

}
