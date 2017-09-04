package prj.serenasimon.walkdog;

import java.io.IOException;
import java.util.AbstractMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import prj.serenasimon.socket.ChatClient;
import prj.serenasimon.socket.ChatServer;

@Path("chat")
public class ChatResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        return Response.ok("OKOK").build();
    }

    @POST
    @Path("createChatroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatroom() throws IOException {
        ChatServer server = new ChatServer();
        ChatClient client = new ChatClient(server.getAddr(), server.getPort());
        return Response.ok(new AbstractMap.SimpleEntry<ChatServer, ChatClient>(server, client)).build();
    }

    @POST
    @Path("joinChatroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinChatroom() {
        return Response.ok("Join ChatRoom done").build();
    }

    @POST
    @Path("exitChatroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exitChatroom() {
        return Response.ok("Exit ChatRoom done").build();
    }
}
