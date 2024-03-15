package nl.c2c.ac.devops.auditcase.startstop;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;

import javax.inject.Inject;

import nl.c2c.ac.devops.auditcase.AcExecDomino;
import nl.c2c.ac.devops.auditcase.AuditCasePluginConfiguration;

/**
 *
 */
public class StartHttp extends DefaultTask {

    private final ExecOperations execOperations;

    // Inject into the constructor
    @Inject
    public StartHttp(final ExecOperations execOperations) {
        super();
        this.execOperations = execOperations;
    }

    @TaskAction
    public void call() {
        final AuditCasePluginConfiguration auditCasePluginConfiguration = AuditCasePluginConfiguration.getInstance(this.getProject());
        AcExecDomino.call(auditCasePluginConfiguration, execOperations, "load http");
    }
}
