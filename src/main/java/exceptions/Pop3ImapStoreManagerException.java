package exceptions;

public class Pop3ImapStoreManagerException extends MailException {

    public Pop3ImapStoreManagerException(Throwable e) {
        super(e);
    }

    public Pop3ImapStoreManagerException(String message) {
        super(message);
    }

    public Pop3ImapStoreManagerException(String message, Throwable e) {
        super(message, e);
    }
}
