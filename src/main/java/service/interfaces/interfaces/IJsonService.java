package service.interfaces.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import exceptions.ConfigurationException;
import exceptions.RepositoryServiceException;

import java.util.HashMap;

public interface IJsonService {
    /**
     * Initialise output directory to store mails
     *
     * @throws RepositoryServiceException
     */
    Boolean initializeOutputDir() throws RepositoryServiceException, ConfigurationException;

    public ArrayNode creationJsonEmail(HashMap hashMap) throws JsonProcessingException ;
}
