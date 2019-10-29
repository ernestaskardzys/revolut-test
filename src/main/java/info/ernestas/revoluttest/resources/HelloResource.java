package info.ernestas.revoluttest.resources;

import info.ernestas.revoluttest.model.Greeting;
import info.ernestas.revoluttest.service.GreetingService;
import org.glassfish.jersey.server.ManagedAsync;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    private GreetingService greetingService;

    @Inject
    public HelloResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void hello(@PathParam("param") String name, @Suspended AsyncResponse asyncResponse) {
        Greeting greeting = greetingService.getGreeting(name);
        asyncResponse.resume(greeting);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String helloUsingJson(Greeting greeting) {
        return greeting.getName() + "\n";
    }
}
