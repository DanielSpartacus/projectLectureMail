package model;

import lombok.NonNull;
import lombok.Singular;

import java.util.List;

public class Mail <T> {
    /**
     * Mail sender
     */
    @NonNull
    private String from;

    /**
     * Mail subject
     */
    private String subject;

    /**
     * Mail content
     */
    private String body;

    /**
     * The list of attachments if any
     */
    @Singular
    private List<Attachement> attachments;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachement> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachement> attachments) {
        this.attachments = attachments;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    /**
     * The original message
     */
    private T message;


}
