package service.interfaces.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import configuration.Configuration;
import exceptions.ConfigurationException;
import exceptions.RepositoryServiceException;
import org.apache.commons.io.FileUtils;
import service.interfaces.interfaces.IEnregistrementMailService;
import service.interfaces.interfaces.IJsonService;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Optional;

public class IJsonServiceImpl  implements IJsonService {


    private File outputDirectory;

    /**
     * Unique instance du repository.
     */
    private static IJsonService REPOSITORY_SERVICE_INSTANCE;
    public static final String OUTPUT_DIRECTORY_CLEANING_FAILED_ERROR = "The output directory couldn't be cleaned";

    /**
     * Get the unique instance of the {@link IEnregistrementMailIServiceImpl}
     *
     * @return The unique instance
     */
    public static IJsonService getInstance() {
        if (REPOSITORY_SERVICE_INSTANCE == null) {
            REPOSITORY_SERVICE_INSTANCE = new IJsonServiceImpl();
        }
        return REPOSITORY_SERVICE_INSTANCE;
    }
    @Override
    public Boolean initializeOutputDir() throws RepositoryServiceException, ConfigurationException {
        outputDirectory = new File(Configuration.getInstance().getOutputDirPath());

        // If the directory already exist (Not a new one), clean it
        if (outputDirectory.exists()) {

            try {
                FileUtils.cleanDirectory(outputDirectory);
            }
            catch (IOException e) {
                throw new RepositoryServiceException(OUTPUT_DIRECTORY_CLEANING_FAILED_ERROR);
            }
        }

        return Boolean.TRUE;
    }
    @Override
    public ArrayNode creationJsonEmail(HashMap hashMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode1 = mapper.createObjectNode();
        /**
         * Create three JSON Objects objectNode1, objectNode2
         * Add all these three objects in the array
         */
        if (Optional.ofNullable(hashMap ).isPresent()){

            objectNode1.put("id", hashMap.get("id").toString());
            objectNode1.put("subject", hashMap.get("SUBJECT").toString());

            arrayNode.add(objectNode1);

        };
        return arrayNode;
    }
}
