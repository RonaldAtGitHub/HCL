package nl.c2c.ac.devops.auditcase;

public class AcGenericRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AcGenericRuntimeException(final String paramString) {
        super(paramString);
    }

    public AcGenericRuntimeException(final Throwable paramThrowable) {
        super(paramThrowable);
    }

}
