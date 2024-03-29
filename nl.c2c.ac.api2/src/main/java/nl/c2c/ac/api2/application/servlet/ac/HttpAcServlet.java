package nl.c2c.ac.api2.application.servlet.ac;

import lombok.extern.slf4j.Slf4j;
import lotus.domino.NotesThread;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

//Called by com.ibm.ws.webcontainer.servlet
@Slf4j
public class HttpAcServlet extends HttpServlet { //TODO this was old version of Jersey ServletContainer {

    private static final long serialVersionUID = 1L;

    public HttpAcServlet() {
        //TODO Jersey was responsible for our resources
//        super(new ResourcesAcConfiguration());
    }

    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
        try {
            NotesThread.sinitThread();
            //TODO here we did redirect o jersey code
            // super.service(req, res);
            try (PrintWriter writer = res.getWriter()) {
                writer.println("Hello, world!");
            }
        } catch (final Exception exception) {
            log.error(exception.getMessage(), exception);
        } finally {
            NotesThread.stermThread();
        }

    }
}
