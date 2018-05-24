package filtreMail;

import configuration.Configuration;
import constants.FiltreMailConstant;
import exceptions.ConfigurationException;
import exceptions.MailException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.SearchTerm;
import java.io.IOException;

public class Pop3ImapMailMatcher extends SearchTerm {

    @Override
    public boolean match(Message message) {

        Boolean isValidMessage = Boolean.FALSE;

        if (message != null) {

            String sender = null;
            String subjectFilter = null;
            String hasAttachmentsFilter = null;

            try {
                sender = Configuration.getInstance().getPropertyValueAsString(FiltreMailConstant.MAIL_FILTER_FROM);
                subjectFilter = Configuration.getInstance().getPropertyValueAsString(FiltreMailConstant.MAIL_FILTER_SUBJECT);
                hasAttachmentsFilter = Configuration.getInstance().getPropertyValueAsString(FiltreMailConstant.MAIL_FILTER_SUBJECT);
            }
            catch (ConfigurationException e) {
                // log an error using self4j extention in lambok tool;
            }

            try {
                Address[] a;
                String receveur= "";
                // Verifying the sender filter`
                if ((a = message.getRecipients(Message.RecipientType.TO)) != null) {

                    for (int j = 0; j < a.length; j++)
                        receveur=a[j].toString();

                }
                if (StringUtils.isNotEmpty(sender) &&
                        sender.equals(receveur) ){
                    isValidMessage = Boolean.TRUE;
                }

                // If the sender filter is verified, and other filters exist, processed the verification from them
                if (isValidMessage && (StringUtils.isNotEmpty(subjectFilter) || StringUtils.isNotEmpty(hasAttachmentsFilter))) {

                    // Verifying the mail subject filter
                    if (StringUtils.isNotBlank(subjectFilter) && !message.getSubject().contains(subjectFilter)) {
                        isValidMessage = Boolean.FALSE;
                    }

                    // Verifying the attachments filter
                    if (isValidMessage && StringUtils.isNotEmpty(hasAttachmentsFilter)) {

                        try {

                            Boolean hasAttachments = BooleanUtils.toBoolean(hasAttachmentsFilter,
                                    Boolean.TRUE.toString(),
                                    Boolean.FALSE.toString());

                            Boolean attachmentsFound = Boolean.FALSE;

                            // Get the attachments
                            if (message.getContent() instanceof Multipart) {

                                Multipart multipart = (Multipart) message.getContent();

                                Integer i = 0;

                                while (Boolean.FALSE.equals(attachmentsFound) && i < multipart.getCount()) {

                                    BodyPart bodyPart = multipart.getBodyPart(i);

                                    if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                                            StringUtils.isNotEmpty(bodyPart.getFileName())) {
                                        attachmentsFound = Boolean.TRUE;
                                    }
                                    else {
                                        i++;
                                    }
                                }

                                isValidMessage = attachmentsFound.equals(hasAttachments);
                            }
                        }
                        catch (IllegalArgumentException e) {
                            new MailException(String.format("Attachments filtering failed " +
                                    "current value : '%s' - expected value : '%s' or '%s'", hasAttachmentsFilter, Boolean.TRUE, Boolean.FALSE));
                        }
                    }
                }
            }
            catch (MessagingException | IOException e) {
                isValidMessage = Boolean.FALSE;
            }
        }

        return isValidMessage;
    }
}
