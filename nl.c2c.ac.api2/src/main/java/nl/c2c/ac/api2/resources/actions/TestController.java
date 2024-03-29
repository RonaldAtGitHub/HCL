package nl.c2c.ac.api2.resources.actions;

//TODO jakararta!!
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


import nl.c2c.ac.service.Testservice;

@Path("/test")
@Slf4j
public class TestController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getActionDefinitionKey() {

        final Testservice testService = new Testservice();
        return testService.getTest();
    }
}
