package exceptions;

public class EwsStoreManagerException extends MailException {
    public EwsStoreManagerException(Throwable e) {
        super(e);
    }

    public EwsStoreManagerException(String message) {
        super(message);
    }

    public EwsStoreManagerException(String message, Throwable e) {
        super(message, e);
    }
}
