package nl.c2c.ac.bootstrap;

import org.osgi.framework.BundleContext;

import java.util.HashSet;
import java.util.Set;

import nl.c2c.ac.osgi.AbstractActivator;
import nl.c2c.ac.osgi.AcActivator;

public class Activator extends AbstractActivator {

    public Activator() {
        super();
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        super.start(bundleContext);

        final Set<Class<? extends AcActivator>> load = this.getClassesToAutoStart();
        load.forEach((c) -> c.getClass());

    }

    public Set<Class<? extends AcActivator>> getClassesToAutoStart() {

        final Set<Class<? extends AcActivator>> load = new HashSet<>();
        load.add(nl.c2c.ac.Activator.class);

        load.add(nl.c2c.ac.api2.Activator.class);

        return load;
    }

}
