/**
 *
 */
package nl.c2c.ac.bootstrap;

import com.ibm.designer.runtime.domino.adapter.HttpService;
import com.ibm.designer.runtime.domino.adapter.IServiceFactory;
import com.ibm.designer.runtime.domino.adapter.LCDEnvironment;

/**
 * This class is specified as com.ibm.xsp.adapter.serviceFactory to trigger an "autostart" on server start.
 * This triggers the Activator that does the rest of the the startup process
 */
public class AutoStart implements IServiceFactory {

    public AutoStart() {
    }

    @Override
    public HttpService[] getServices(final LCDEnvironment paramLCDEnvironment) {
        final HttpService[] ret = new HttpService[1];
        ret[0] = new StartupHandler(paramLCDEnvironment);
        return ret;
    }

}
