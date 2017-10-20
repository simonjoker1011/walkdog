package prj.serenasimon.walkdog;

import java.util.ArrayList;
import java.util.Map.Entry;

import javax.websocket.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import prj.serenasimon.cache.ChatCache;

@Path("chat")
@Consumes(MediaType.APPLICATION_JSON)
public class ChatResource {
    private static final Logger logger = LogManager.getLogger(ChatResource.class);

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getChatInfo() {
    // JSONObject jsonObject = new JSONObject();
    // for (Entry<String, ArrayList<Session>> entry : ChatCache.getChatSessions().entrySet()) {
    // jsonObject.put(entry.getKey(), new JSONArray(
    // entry.getValue().stream().map(s -> s.getId()).toArray()));
    // }
    // return Response.ok(jsonObject.toString()).build();
    // }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOnlineUserInfo() {
        JSONObject jsonObject = new JSONObject();
        for (Entry<Long, Session> entry : ChatCache.getOnlineUsers().entrySet()) {
            if (((ArrayList<Long>) entry.getValue().getUserProperties().get("reveivers")).size() == 0) {
                jsonObject.put(entry.getKey().toString(), "");
            } else {
                jsonObject.put(entry.getKey().toString(), new JSONArray(entry.getValue().getUserProperties().get("reveivers")));
            }
        }
        return Response.ok(jsonObject.toString()).build();
    }
}
