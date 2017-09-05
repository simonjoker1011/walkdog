package prj.serenasimon.walkdog;

import java.net.MalformedURLException;
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

import prj.serenasimon.datas.User;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger logger = LogManager.getLogger(UserResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        return Response.ok("OKOK").build();
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

        logger.info("\n ID: {} \n Name: {} \n Firstname: {} \n Lastname: {} \n Agerange: {}, \n Link: {} \n Picture: {}", id, name, firstname, lastname, agerange, link, picture);
        // System.out.println(id + "\n " + name + "\n " + firstname + "\n " + lastname + "\n " + agerange + "\n " + link + "\n "
        // + picture + "\n ");

        User user = new User();
        try {
            user = new User(id, name, firstname, lastname, agerange, new URL(link), new URL(picture));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.ok(user).build();
    }
}
