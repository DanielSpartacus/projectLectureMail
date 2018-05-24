package exceptions;

public class StoreManagerException extends MailException {
    public StoreManagerException(Throwable e) {
        super(e);
    }

    public StoreManagerException(String message) {
        super(message);
    }

    public StoreManagerException(String message, Throwable e) {
        super(message, e);
    }
}
