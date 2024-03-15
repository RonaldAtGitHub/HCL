package nl.c2c.ac.devops.auditcase;

public enum AcGradleTask {
    BUILD_AUDITCASE("buildAuditCase"),
    // build  the auditcase.prod.js and auditcase.prod.css
    // minify the auditcase.prod.js  -> auditcase.js
    // minify the auditcase.prod.css -> auditcase.css
    BUILD_AND_MINIMIZE_FRONTEND("buildAndMinimizeFrontEnd"),
    ACSTOPNHTTP("acstopnhttp"),
    ACSTARTNHTTP("acstartnhttp"),
    ACCOPYDOMINO("acCopyDomino"),
    ACCLEAR("acClear"),
    DEPLOYAUDITCASE("deployAuditCase"),
    BUILD("build"), // Only use this if you want ALL unit tests to run
    ASSEMBLE("assemble"),
    CLEAN("clean");

    private final String acGradleTaskString;

    AcGradleTask(final String acGradleTaskString) {
        this.acGradleTaskString = acGradleTaskString;
    }

    @Override
    public String toString() {
        return this.acGradleTaskString;
    }

}
