package prj.serenasimon.chat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import prj.serenasimon.datas.ChatMessage;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

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
            .add("sender", chatMessage.getSender())
            .add("received", chatMessage.getReceived().toString()).build()
            .toString();
    }

}
