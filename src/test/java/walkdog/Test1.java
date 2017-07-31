package walkdog;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import prj.serenasimon.filter.SessionFilter;
import prj.serenasimon.walkdog.UserResource;

public class Test1 extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class, SessionFilter.class);
    }

    @Test
    public void test1() {

        Response response = target("user").request().get();
        response.readEntity(String.class);
        System.out.println("WTH");
    }
}
