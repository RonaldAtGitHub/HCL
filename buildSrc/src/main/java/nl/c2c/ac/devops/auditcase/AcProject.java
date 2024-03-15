package nl.c2c.ac.devops.auditcase;

import java.nio.file.Path;
import java.util.Objects;

public enum AcProject {
    AC("nl.c2c.ac"),
    RESOURCESFOLDER("resources"),
    DEVOPS("nl.c2c.ac.devops"),
    API2("nl.c2c.ac.api2"),
    BOOTSTRAP("nl.c2c.ac.bootstrap"),
    CONNECT("nl.c2c.ac.connect"),
    INSTALLER("nl.c2c.ac.installer"),
    JOBS("nl.c2c.ac.jobs"),
    RESOURCES("nl.c2c.ac.resources"),
    SCHEDULER("nl.c2c.ac.scheduler"),
    TESTS("nl.c2c.ac.tests"),
    ROOT("AuditCase");

    private final String artifactId;

    private Path path = null; //load lazy because of timing issues

    AcProject(final String artifactId) {
        this.artifactId = artifactId;
    }

    public Path getPath(final Path auditCaseProjectRoot) {
        if (Objects.isNull(path)) {
            path = auditCaseProjectRoot.resolve(artifactId);
        }
        return path;
    }

    public String getArtifactId() {
        return artifactId;
    }
}
