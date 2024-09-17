package nl.c2c.ac.api2.application.servlet.ac;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import lotus.domino.NotesThread;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.IOException;

@Slf4j
public class HttpAcServletJakarta extends ServletContainer {

    public HttpAcServletJakarta() {
        super(new ResourcesAcConfiguration());
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {

            NotesThread.sinitThread();

            super.service(req, res);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            NotesThread.stermThread();
        }
    }

}
