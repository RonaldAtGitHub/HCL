package nl.c2c.ac.devops.auditcase;

import lombok.extern.slf4j.Slf4j;
import org.gradle.process.ExecOperations;
import org.gradle.process.ExecResult;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

@Slf4j
public final class AcExecDomino {

    private AcExecDomino() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public static ExecResult call(final AuditCasePluginConfiguration auditCasePluginConfiguration, final ExecOperations execOperations, final String command) {
        try {
            log.info(auditCasePluginConfiguration.getDominoPath().toString());

            final List<String> commandArray = Arrays.asList("cmd", "/c",
              "nserver.exe",
              "-C",
              String.format("\"%s\"", command));

            log.info(String.join(",", commandArray));

            return execOperations.exec(execSpec -> {
                execSpec.setWorkingDir(auditCasePluginConfiguration.getDominoPath());
                execSpec.setCommandLine(commandArray);
                execSpec.setIgnoreExitValue(true);
            });

        } catch (final Exception exception) {
            log.error(exception.getMessage(), exception);
        }

        return null;
    }

}
