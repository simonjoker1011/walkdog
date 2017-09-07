package prj.serenasimon.walkdog;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import prj.serenasimon.cache.ChatCache;
import prj.serenasimon.socket.ChatClient;
import prj.serenasimon.socket.ChatServer;
import prj.serenasimon.socket.Conversation;

@Path("chat")
public class ChatResource {
    private static final Logger logger = LogManager.getLogger(ChatResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        return Response.ok("OKOK").build();
    }

    @POST
    @Path("createChatroom")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatroom(
        @FormParam("userid") String userid) throws IOException {

        ChatServer server = new ChatServer();
        server.getParticipants().add(userid);
        server.launch();

        ChatClient client = new ChatClient(server, userid);

        String JsonResponse = new JSONObject()
            .put("serverid", server.getServerID())
            .put("address", server.getAddr())
            .put("port", server.getPort())
            .put("clientid", client.getClientID())
            .put("participant", client.getParticipant())
            .toString();

        return Response.ok(JsonResponse).build();
    }

    @POST
    @Path("joinChatroom")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinChatroom(
        @FormParam("chatroomid") UUID chatroomid,
        @FormParam("userid") String userid) throws IOException {

        ChatServer server = ChatCache.getChatServer().get(chatroomid);
        server.getParticipants().add(userid);

        ChatClient client = new ChatClient(server, userid);

        String JsonResponse = new JSONObject()
            .put("serverid", server.getServerID())
            .put("clientid", client.getClientID())
            .put("participants", server.getParticipants())
            .toString();

        return Response.ok(JsonResponse).build();
    }

    @POST
    @Path("terminateChatroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateChatroom(
        @FormParam("chatroomid") UUID chatroomid) {
        return Response.ok("Terminate Chatroom done").build();
    }

    @POST
    @Path("exitChatroom")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exitChatroom(
        @FormParam("clientid") UUID clientid,
        @FormParam("userid") String userid) {

        ChatClient chatClient = ChatCache.getChatClient().get(clientid);
        ChatServer chatServer = ChatCache.getChatServer().get(chatClient.getServerID());
        Conversation conversation = ChatCache.getConversations().get(chatServer.getServerID());

        chatServer.getParticipants().remove(userid);
        chatClient.setParticipant(null);
        conversation.getParticipants().remove(userid);

        ChatCache.getChatClient().remove(chatClient.getClientID());

        return Response.ok("Exit ChatRoom done").build();
    }

    @POST
    @Path("sendMessage")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(
        @FormParam("clientid") UUID clientid,
        @FormParam("content") String content) {

        try {
            ChatCache.getChatClient().get(clientid).sendMessage(content);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        return Response.ok(content).build();
    }

    @GET
    @Path("cacheStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage() {

        String JsonResponse = new JSONObject()
            .put("servers", ChatCache.getChatServer().keySet())
            .put("client", ChatCache.getChatClient().keySet())
            .put("connection size", ChatCache.getConversations().size())
            .put("connections", new JSONArray(
                ChatCache.getChatClient().entrySet().stream()
                    .map(e -> e.getValue().getConnInfo())
                    .toArray()))
            .toString();

        return Response.ok(JsonResponse).build();
    }
}
