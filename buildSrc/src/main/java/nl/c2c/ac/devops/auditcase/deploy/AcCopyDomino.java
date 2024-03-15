package nl.c2c.ac.devops.auditcase.deploy;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

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
public class AcCopyDomino extends DefaultTask {

    private final FileSystemOperations fileSystemOperations;

    // Inject into the constructor
    @Inject
    public AcCopyDomino(final FileSystemOperations fileSystemOperations) {
        super();
        this.fileSystemOperations = fileSystemOperations;
    }

    @TaskAction
    public void call() {
        final AuditCasePluginConfiguration auditCasePluginConfiguration = AuditCasePluginConfiguration.getInstance(this.getProject());
        AcCopyDeploy.call(auditCasePluginConfiguration, fileSystemOperations, "plugins");
        AcCopyDeploy.call(auditCasePluginConfiguration, fileSystemOperations, "features");
    }

}
