package nl.c2c.ac.devops.auditcase.deploy;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.tasks.WorkResult;

import java.nio.file.Path;
import java.nio.file.Paths;

import nl.c2c.ac.devops.auditcase.AuditCasePluginConfiguration;

/**
 * nl.c2c.ac.plugins.dir = ..\\nl.c2c.ac.updatesite.server.dev\\target\\repository\\plugins
 * nl.c2c.ac.features.dir = ..\\nl.c2c.ac.updatesite.server.dev\\target\\repository\\features
 * #deploy to dev01 server
 * nl.c2c.ac.plugins.dir.dev01=E:\\DominoData\\domino\\workspace\\applications\\eclipse\\plugins\\
 * nl.c2c.ac.features.dir.dev01=E:\\DominoData\\domino\\workspace\\applications\\eclipse\\features\\
 * <p>
 * nl.c2c.ac.plugins.dir.dev02=C:\\PROGRA~1\\IBM\\Domino\\data\\domino\\workspace\\applications\\eclipse\\plugins\\
 * nl.c2c.ac.features.dir.dev02=C:\\PROGRA~1\\IBM\\Domino\\data\\domino\\workspace\\applications\\eclipse\\features\\
 */
@Slf4j
public class AcDeleteDeploy {

    private AcDeleteDeploy() {
        throw new UnsupportedOperationException();
    }

    public static void call(final AuditCasePluginConfiguration auditCasePluginConfiguration, final FileSystemOperations fileSystemOperations, final String folder) {

        final Path delete = auditCasePluginConfiguration.getDominoDataPath().resolve(Paths.get("domino", "workspace", "applications", "eclipse", folder));
        log.info("delete %s ", delete);

        final WorkResult workResult = fileSystemOperations.delete(spec -> {
            spec.delete(delete);
        });

    }

}
