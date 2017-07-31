package prj.serenasimon.walkdog;

import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import prj.serenasimon.datas.User;

@Path("user")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        return Response.ok("OKOK").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response signupwithfacebook(
        @QueryParam("id") Long id,
        @QueryParam("name") String name,
        @QueryParam("firstname") String firstname,
        @QueryParam("lastname") String lastname,
        @QueryParam("agerange") Integer agerange,
        @QueryParam("link") URL link,
        @QueryParam("picture") URL picture,
        @QueryParam("cover") URL cover) {

        User user = new User(id, name, firstname, lastname, agerange, link, picture, cover);

        return Response.ok(user).build();
    }
}
