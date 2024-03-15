package nl.c2c.ac.api2.resources.actions;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
