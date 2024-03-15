package nl.c2c.ac.bootstrap;

import com.ibm.designer.runtime.domino.adapter.ComponentModule;
import com.ibm.designer.runtime.domino.adapter.HttpService;
import com.ibm.designer.runtime.domino.adapter.LCDEnvironment;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletRequestAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletResponseAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpSessionAdapter;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;

/**
 * This is actually an dummy object just to activate the service
 */
public class StartupHandler extends HttpService {

    public StartupHandler(final LCDEnvironment lcdEnv) {
        super(lcdEnv);
    }

    @Override
    public int getPriority() {
        return 1000; // the higher the later this service will queried
    }

    @Override
    public void getModules(final List<ComponentModule> paramList) {
    }

    @Override
    public boolean doService(final String arg0, final String arg1, final HttpSessionAdapter arg2,
                             final HttpServletRequestAdapter arg3, final HttpServletResponseAdapter arg4)
      throws ServletException, IOException {
        return false;
    }

}
