package exceptions;


public class JsonException extends MailException{

    public JsonException(Throwable e) {
        super(e);
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable e) {
        super(message, e);
    }
}
