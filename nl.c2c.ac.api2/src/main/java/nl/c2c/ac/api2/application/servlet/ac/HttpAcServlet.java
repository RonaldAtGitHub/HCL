package nl.c2c.ac.api2.application.servlet.ac;

import lombok.extern.slf4j.Slf4j;
import lotus.domino.NotesThread;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

/**
 * The equivalent of the OSGi Http Services "servlet" in registerServlet. The Servlet is instantiated by the Extension Point provider and must be an instance of javax.servlet.Servlet
 */
@Slf4j
public class HttpAcServlet extends ServletContainer {

    private static final long serialVersionUID = 1L;

    public HttpAcServlet() {
        super(new ResourcesAcConfiguration());
    }

    @Override
    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
        try {
            NotesThread.sinitThread();
            super.service(req, res);
        } catch (final Exception exception) {
            log.error(exception.getMessage(), exception);
        } finally {
            NotesThread.stermThread();
        }

    }
}
