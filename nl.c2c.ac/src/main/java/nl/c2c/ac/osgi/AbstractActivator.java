package nl.c2c.ac.osgi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractActivator extends Plugin implements AcActivator {

    private Logger logActivator = null;

    public AbstractActivator() {
        super();
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {

        System.out.println("AbstractActivator.start " + this.getClass().getCanonicalName());

        super.start(bundleContext);
    }
}
