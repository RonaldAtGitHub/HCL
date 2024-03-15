package nl.c2c.ac.devops.auditcase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditCaseProp {

    private final String acBundle;

    private final String acVersion;

    private final String acVersionZip;

    private final String acQualifier;
}
