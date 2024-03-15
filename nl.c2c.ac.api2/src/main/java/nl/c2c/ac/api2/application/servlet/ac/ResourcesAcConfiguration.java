package nl.c2c.ac.api2.application.servlet.ac;

import nl.c2c.ac.api2.application.servlet.AbstractResourcesConfiguration;
import nl.c2c.ac.api2.resources.actions.TestController;

/**
 * This class is responsible for making resources available to the api.
 */
public class ResourcesAcConfiguration extends AbstractResourcesConfiguration {

    public ResourcesAcConfiguration() {
        super();

        // this is a registration of individual classes
        registerResources();

    }

    /**
     * Internal resources for AuditCase
     * <p>
     * Please add to list alphabetically
     */
    private void registerResources() {
        register(TestController.class);
    }


}
