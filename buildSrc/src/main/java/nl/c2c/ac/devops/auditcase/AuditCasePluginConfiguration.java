package nl.c2c.ac.devops.auditcase;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Value
@Builder
@Slf4j
public class AuditCasePluginConfiguration {

    Path dominoPath;

    Path dominoDataPath;

    Path auditCaseProjectRoot;

    public static AuditCasePluginConfiguration getInstance(final Project project) {

        final AuditCasePluginConfigurationBuilder builder = builder();

        builder.dominoPath(convertToShortPath(getProp(project, "acdevops.domino")));// we configure a default in gradle.properties use ENV ORG_GRADLE_PROJECT_acdevops.domino or -Pacdevops.domino to overwrite
        builder.dominoDataPath(convertToShortPath(getProp(project, "acdevops.dominodata")));


        // based on project get project root to find projects files during build
        final Path auditCaseProjectRoot = project.getRootDir().toPath();
        builder.auditCaseProjectRoot(auditCaseProjectRoot);

        log.info("Setting up configuration for " + auditCaseProjectRoot);

        return builder.build();
    }

    private static Path convertToShortPath(String prop) {
        prop = prop.replace("(?i)(program\\sfiles)", "PROGRA~1");
        prop = prop.replace("\\*", "\\");
        return Paths.get(prop);
    }

    private static String getProp(final Project project, final String key) {
        final Optional<String> property = getOptionalProp(project, key);
        return property.orElseThrow(() -> new AcGenericRuntimeException("Missing property " + key));
    }

    private static Optional<String> getOptionalProp(final Project project, final String key) {
        final Map<String, ?> projectProp = project.getProperties();
        if (project.hasProperty(key)) {
            return Optional.of((String) projectProp.get(key));
        }
        return Optional.empty();

    }

}
