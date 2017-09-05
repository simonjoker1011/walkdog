package prj.serenasimon.walkdog;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import prj.serenasimon.cache.ChatCache;
import prj.serenasimon.socket.ChatClient;
import prj.serenasimon.socket.ChatServer;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatroom(
        @QueryParam("userid") String userid) throws IOException {

        ChatServer server = new ChatServer();
        server.getParticipants().add(userid);

        ChatServer serverInCache = ChatCache.getChatServer().get(server.getServerID());
        ChatClient client = new ChatClient(server.getServerID(), serverInCache.getAddr(), serverInCache.getPort());
        client.setParticipant(userid);

        String JsonResponse = new JSONObject()
            .put("serverid", serverInCache.getServerID())
            .put("address", serverInCache.getAddr())
            .put("port", serverInCache.getPort())
            .put("clientid", client.getClientID())
            .put("participant", client.getParticipant()).toString();

        return Response.ok(JsonResponse).build();
    }

    @POST
    @Path("joinChatroom/{chatroomid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinChatroom(
        @PathParam("chatroomid") UUID chatroomid,
        @QueryParam("userid") String userid) throws IOException {

        ChatServer server = ChatCache.getChatServer().get(chatroomid);
        server.getParticipants().add(userid);
        ChatClient client = new ChatClient(server.getServerID(), server.getAddr(), server.getPort());
        client.setParticipant(userid);

        String JsonResponse = new JSONObject()
            .put("serverid", server.getServerID())
            .put("clientid", client.getClientID())
            .put("participants", server.getParticipants())
            .toString();

        return Response.ok(JsonResponse).build();
    }

    @POST
    @Path("terminalChatroom/{chatroomid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminalChatroom(
        @PathParam("chatroomid") UUID chatroomid) {
        return Response.ok("Terminal ChatRoom done").build();
    }

    @POST
    @Path("sendMessage/{serverid}/{clientid}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(
        @PathParam("serverid") UUID serverid,
        @PathParam("clientid") UUID clientid,
        @FormParam("content") String content) {

        try {
            ChatCache.getChatClient().get(clientid).sendMessage(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Response.serverError().build();
        }
        ;

        return Response.ok(content).build();
    }
}
