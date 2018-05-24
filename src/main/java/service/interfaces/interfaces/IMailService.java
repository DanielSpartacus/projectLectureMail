package service.interfaces.interfaces;

import exceptions.RepositoryServiceException;
import model.Mail;

import java.util.HashMap;

public  interface IMailService {

    void processMail(Mail mail,int index) throws RepositoryServiceException;


}
