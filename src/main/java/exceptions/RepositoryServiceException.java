package exceptions;

public class RepositoryServiceException extends MailException{
    public RepositoryServiceException(String message) {
        super(message);
    }

    public RepositoryServiceException(Throwable t) {
        super(t);
    }

    public RepositoryServiceException(String message, Throwable t) {
        super(message, t);
    }
}
