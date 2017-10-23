package prj.serenasimon.walkdog;

import java.net.URL;

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
import prj.serenasimon.datas.User;
import prj.serenasimon.util.HibernateUtil;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger logger = LogManager.getLogger(UserResource.class);

    @GET
    @Path("online")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOnlineUsers() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("online", new JSONArray(ChatCache.getOnlineUsers().keySet()));
        // for (Long userid : ChatCache.getOnlineUsers().keySet()) {
        // ArrayList<Long> receivers = (ArrayList<Long>) entry.getValue().getUserProperties().get("reveivers");
        // jsonObject.put(entry.getKey().toString(), new JSONArray(receivers.toArray()));
        //
        // }

        return Response.ok(jsonObject.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signupwithfacebook(
        @FormParam("id") Long id,
        @FormParam("name") String name,
        @FormParam("firstname") String firstname,
        @FormParam("lastname") String lastname,
        @FormParam("agerange") Integer agerange,
        @FormParam("link") String link,
        @FormParam("picture") String picture) {

        logger.info("\n ID: {} \n Name: {} \n Firstname: {} \n Lastname: {} \n Agerange: {}, \n Link: {} \n Picture: {}",
            id, name, firstname, lastname, agerange, link, picture);
        User user = null;
        try {
            user = (User) HibernateUtil.basicReadById(User.class, id);
            logger.info("{} logging in...", name);

            if (user == null) {
                logger.info("New user: {}, id: {}", name, id);
                user = new User(id, name, firstname, lastname, agerange, new URL(link), new URL(picture));
                HibernateUtil.basicCreate(user);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.ok(user).build();
    }

}
