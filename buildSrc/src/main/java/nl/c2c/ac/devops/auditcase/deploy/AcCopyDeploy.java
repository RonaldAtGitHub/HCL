package nl.c2c.ac.devops.auditcase.deploy;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.tasks.WorkResult;

import java.nio.file.Path;
import java.nio.file.Paths;

import nl.c2c.ac.devops.auditcase.AcProject;
import nl.c2c.ac.devops.auditcase.AuditCasePluginConfiguration;

@Slf4j
public class AcCopyDeploy {

    private AcCopyDeploy() {
        throw new UnsupportedOperationException();
    }

    public static void call(final AuditCasePluginConfiguration auditCasePluginConfiguration, final FileSystemOperations fileSystemOperations, final String folder) {

        final Path from = AcProject.BOOTSTRAP.getPath(auditCasePluginConfiguration.getAuditCaseProjectRoot()).resolve(Paths.get("build", folder));
        final Path into = auditCasePluginConfiguration.getDominoDataPath().resolve(Paths.get("domino", "workspace", "applications", "eclipse", folder));

        log.info("from %s ", from);
        log.info("into %s ", into);

        final WorkResult workResult = fileSystemOperations.copy(spec -> {
            spec.from(from);
            spec.into(into);
        });

    }

}
