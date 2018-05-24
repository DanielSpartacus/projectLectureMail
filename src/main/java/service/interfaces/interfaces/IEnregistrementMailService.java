package service.interfaces.interfaces;

import com.fasterxml.jackson.databind.node.ArrayNode;
import exceptions.ConfigurationException;
import exceptions.RepositoryServiceException;

import java.util.HashMap;

public interface IEnregistrementMailService {

    /**
     * Initialise output directory to store mails
     *
     * @throws RepositoryServiceException
     */
    Boolean initializeOutputDir() throws RepositoryServiceException, ConfigurationException;

    /**
     * Write the properties in the appropriate folder
     * @param hashMap Map properties mail
     * @throws RepositoryServiceException
     */
    public  void enregistrementEmail(HashMap hashMap) throws RepositoryServiceException ;


    /**
     * Write the properties in the appropriate folder
     *
     * @param hashMap Map properties mail
     * @param arrayNode array's prOperties Mail (id,subject)
     * @throws RepositoryServiceException
     */
    public  void enregistrementFichierJson(HashMap hashMap, ArrayNode arrayNode) throws RepositoryServiceException ;

}
