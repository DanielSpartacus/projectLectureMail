package service.interfaces.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.tools.javac.util.List;
import constants.TypeMailConstant;
import exceptions.RepositoryServiceException;
import model.Attachement;
import model.Mail;
import service.interfaces.interfaces.IMailService;

import javax.mail.*;
import java.io.*;
import java.util.Date;
import java.util.HashMap;


public class IMailServiceImpl implements IMailService {

    private static final String JSON_KEY_ID = "id";

    private static final String JSON_KEY_SUBJECT = "subject";

    /**
     * Map of index by sender found in the mail
     */
    private HashMap<String, Long> mapIndexBySender = new HashMap<>();

    private String subject ;

    private ArrayNode arrayNode;

    /**
     * Map of JSOON by sender
     */
    private HashMap<String, Object> mapJsonBySender = new HashMap<>();

    @Override
    public void processMail(Mail mail, int index) throws  RepositoryServiceException {
        if (mail != null) {

            // Get the current index, or create new one if it is not existing

            Address[] a;

            // FROM
            if (mail.getFrom() != null) {

                mapJsonBySender.put("FROM",mail.getFrom());
            }

            // TO
            try {
            if ((a = Message.class.cast(mail.getMessage()).getRecipients(Message.RecipientType.TO)) != null) {
                String receveur= "";
                for (int j = 0; j < a.length; j++)
                    receveur=a[j].toString();

                mapJsonBySender.put("TO",receveur);
            }
            }catch (MessagingException e){
                throw new RepositoryServiceException ("An error occurs when creating receveur", e);
            }

            mapJsonBySender.put("SUBJECT", mail.getSubject());
            mapJsonBySender.put("MESSAGE", (Message)mail.getMessage());


                 mapJsonBySender.put("id", index);
                 mapJsonBySender.put("SUBJECT" , mail.getSubject());
                this.setSubject(mail.getSubject());

                try {
                    this.setArrayNode(IJsonServiceImpl.getInstance().creationJsonEmail(mapJsonBySender));
                    IEnregistrementMailIServiceImpl.getInstance().enregistrementFichierJson(mapJsonBySender,this.getArrayNode());
                }catch (JsonProcessingException e){
                    throw new RepositoryServiceException ("An error occurs when creating array Json", e);
                }

            // Create eml file
            try {
                    IEnregistrementMailIServiceImpl.getInstance().enregistrementEmail(mapJsonBySender);
            }
            catch (RepositoryServiceException e) {
                throw new RepositoryServiceException ("An error occurs when creating eml file", e);
            }

            // Create attachments
           mail.getAttachments();
            if (mail.getAttachments()!=null){
                mail.getAttachments().forEach(message -> {

                    try {
                       // mail.getFrom(),
                               // index,
                               // (Attachement) message);
                        IEnregistrementMailIServiceImpl.getInstance().enregistrementEmail(this.getMapJsonBySender());
                    }
                    catch (RepositoryServiceException e) {
                        // log an error using log4j extention in lambok tool
                    }
                });
            }

        this.setMapJsonBySender(mapJsonBySender);
        }

    }



    public HashMap<String, Object> getMapJsonBySender() {
        return mapJsonBySender;
    }

    public void setMapJsonBySender(HashMap<String, Object> mapJsonBySender) {
        this.mapJsonBySender = mapJsonBySender;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayNode getArrayNode() {
        return arrayNode;
    }

    public void setArrayNode(ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }
}
