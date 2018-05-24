package com.lectureemail.protocol.interfaces;

import exceptions.*;
import model.Mail;

import java.util.List;

public interface IlectureEmail {



        public void init()throws StoreManagerException, EwsStoreManagerException, Pop3ImapStoreManagerException, ConfigurationException;

        public List<Mail> listeMail() throws StoreManagerException, EwsStoreManagerException, Pop3ImapStoreManagerException, ConfigurationException;

}
