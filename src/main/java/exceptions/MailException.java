package exceptions;

public class MailException extends Exception {
    public MailException(Throwable e) {
        super(e);
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable e) {
        super(message, e);
    }
}
