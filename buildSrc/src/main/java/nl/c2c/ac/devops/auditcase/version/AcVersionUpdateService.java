package nl.c2c.ac.devops.auditcase.version;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import nl.c2c.ac.devops.auditcase.AcGenericRuntimeException;
import nl.c2c.ac.devops.auditcase.AcVersion;
import nl.c2c.ac.devops.auditcase.AuditCaseProp;

@Slf4j
public class AcVersionUpdateService {

    public AuditCaseProp getProperties(final Project project) {

        final AcVersion bundleversionmin = getVersion(project);
        final AcVersion bundleversionmax = bundleversionmin.toNewPatch();

        final String bundleVersion = ((("[" + bundleversionmin) + ",") + bundleversionmax.toString()) + ")";

        log.info("use bundle " + bundleversionmin + " in " + project.getName());

        return AuditCaseProp
          .builder()
          .acVersion(bundleversionmin.toString())
          .acVersionZip(bundleversionmin.toStringWithRc())
          .acQualifier(getAcQualifier())
          .acBundle(bundleVersion)
          .build();
    }

    private AcVersion getVersion(final Project project) {

        if (!project.getProperties().isEmpty() && project.hasProperty("buildVersion")) {
            final String buildVersion = (String) project.getProperties().get("buildVersion");
            return AcVersion
              .builder(buildVersion)
              .build();
        } else {
            throw new AcGenericRuntimeException("Please provide project property -PbuildVersion");
        }
    }

    private String getAcQualifier() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        df.setTimeZone(TimeZone.getDefault());

        return df.format(new Date());
    }
}
