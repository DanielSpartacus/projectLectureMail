package com.lectureemail;

import com.lectureemail.protocol.implementation.EwsStoreManager;
import com.lectureemail.protocol.implementation.Pop3ImapStoreManager;
import com.lectureemail.protocol.interfaces.IlectureEmail;
import configuration.Configuration;
import constants.ConfigurationConstants;
import exceptions.MailException;
import model.Mail;
import org.apache.commons.collections4.CollectionUtils;
import service.interfaces.implementation.IEnregistrementMailIServiceImpl;
import service.interfaces.implementation.IMailServiceImpl;
import service.interfaces.interfaces.IMailService;

import java.util.List;

public class CaptureEmails {
    public static void main(String[] args) throws MailException {

        // Fisrt we need to check if everything is ok with the configuration
        Configuration.getInstance().checkConfiguration();

        // Checking the protocol to be used
        String protocol = Configuration.getInstance().getProtocol();

        // Then We should choose the store manager to be used according to the used protocol
        IlectureEmail storeManager;

        switch (protocol) {

            case ConfigurationConstants.MAIL_STORE_PROTOCOL_IMAP:

            case ConfigurationConstants.MAIL_STORE_PROTOCOL_POP3:
                storeManager = new Pop3ImapStoreManager();
                break;

            case ConfigurationConstants.MAIL_STORE_PROTOCOL_MICROSOFT_EWS:
                storeManager = new EwsStoreManager();
                break;

            default:
                storeManager = null;
                break;
        }

        if (storeManager != null) {

            // Initialize the store
            storeManager.init();

            // If the used protocol is Pop3 or Imap, then a connexion to the store must be established
            if (storeManager instanceof Pop3ImapStoreManager) {
                ((Pop3ImapStoreManager) storeManager).openConnection();
            }

            // Fetching the mails using the store
            List<Mail> mails = storeManager.listeMail();


            // Initialise the repository service to write output files and folders
            IEnregistrementMailIServiceImpl.getInstance().initializeOutputDir();

            if (CollectionUtils.isNotEmpty(mails)) {

                Integer totalMessages = mails.size();

                Integer compteurMail = 1;

                // Initialise the mails la gestion du service de mail
                IMailService mailService = new IMailServiceImpl();


                // Parsing and processing the mails
                for (Mail mail : mails) {

                    mailService.processMail(mail,compteurMail);

                    compteurMail=compteurMail + 1;
                }


            }

            // If the protocol used is Pop3 or Imap, then the connexion must be closed
            if (storeManager instanceof Pop3ImapStoreManager) {
                ((Pop3ImapStoreManager) storeManager).closeConnection();
            }
        }
        else {
            throw new MailException(String.format("Unknown protocol '%s'. Closing the application", protocol));
        }

    }

}


