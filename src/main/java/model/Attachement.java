package model;

import javax.mail.BodyPart;

public class Attachement <T>{

    private T attachment;

  public void attachment(T bodyPart){
    this.setAttachment(bodyPart);
  }
    public T getAttachment() {
        return attachment;
    }

    public void setAttachment(T attachment) {
        this.attachment = attachment;
    }
}
