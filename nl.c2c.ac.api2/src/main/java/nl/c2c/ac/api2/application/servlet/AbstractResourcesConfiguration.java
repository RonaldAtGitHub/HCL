package nl.c2c.ac.api2.application.servlet;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;

public abstract class AbstractResourcesConfiguration extends ResourceConfig {

    protected AbstractResourcesConfiguration() {
        super();

        // server validation..
        register(ValidationFeature.class);

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);


        // register important program features
        registerApplicationBinders();

    }

    private void registerApplicationBinders() {


        // upload file feature
        register(MultiPartFeature.class);


        // convert json string to objects (e.g. see {@link WebhookSubscribe#post(nl.c2c.ac.api2.resources.webhook.zapier.WebhookParamsJersey)}
        register(JacksonJsonProvider.class);

    }
}
