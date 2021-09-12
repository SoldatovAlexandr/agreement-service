package edu.strongsubgroup.agreement.exception;

public class AgreementException extends Exception{
    public AgreementException() {
    }

    public AgreementException(String message) {
        super(message);
    }

    public AgreementException(String message, Throwable cause) {
        super(message, cause);
    }
}
