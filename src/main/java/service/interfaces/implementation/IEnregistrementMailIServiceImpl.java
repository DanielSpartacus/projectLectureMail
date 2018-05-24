package service.interfaces.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import configuration.Configuration;
import exceptions.ConfigurationException;
import exceptions.RepositoryServiceException;
import org.apache.commons.io.FileUtils;
import service.interfaces.interfaces.IEnregistrementMailService;

import javax.mail.Message;
import java.io.*;
import java.util.HashMap;

public class IEnregistrementMailIServiceImpl implements IEnregistrementMailService {
    public static final String OUTPUT_DIRECTORY_CLEANING_FAILED_ERROR = "The output directory couldn't be cleaned";

    private File outputDirectory;

    /**
     * Unique instance du repository.
     */
    private static IEnregistrementMailService REPOSITORY_SERVICE_INSTANCE;

    /**
     * Get the unique instance of the {@link IEnregistrementMailIServiceImpl}
     *
     * @return The unique instance
     */
    public static IEnregistrementMailService getInstance() {
        if (REPOSITORY_SERVICE_INSTANCE == null) {
            REPOSITORY_SERVICE_INSTANCE = new IEnregistrementMailIServiceImpl();
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
    public void enregistrementEmail(HashMap hashMap) throws RepositoryServiceException {
        ObjectOutputStream oosMessage;
        ObjectOutputStream oosCorps;

        try {
            String repertoire = outputDirectory.getAbsolutePath() + File.separator + recuperationEmetteur(hashMap);;

            File f1= new File(repertoire);
            if(!f1.exists()){
                f1.mkdirs();
            }
            // enregistrement du message
            String message=hashMap.get("id").toString()+".eml";
            String pathMess=new File(f1.getAbsolutePath()).toString() + "/" + message;
            oosMessage = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File( pathMess))));

            //écriture du message dans le fichier
            Message mesage= (Message)hashMap.get("MESSAGE");
            OutputStream out = new FileOutputStream(new File(pathMess));
            try {
                mesage.writeTo(out);
            }catch (Exception e){
                e.printStackTrace();
            }

            // ferme le flux !
            oosMessage.close();
            // enregistrement du corps de message
            String corps=hashMap.get("id").toString()+".txt";
            String pathCorps=new File(f1.getAbsolutePath()).toString() + "/" + corps;

            oosCorps = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File( pathCorps))));
            oosCorps.writeObject(hashMap.get("CONTENT"));
            oosCorps.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enregistrementFichierJson(HashMap hashMap, ArrayNode arrayNode) throws RepositoryServiceException {
        if ( !hashMap.isEmpty() && arrayNode != null) {
            ObjectMapper mapper = new ObjectMapper();
            String repertoire = outputDirectory.getAbsolutePath() + File.separator + recuperationEmetteur(hashMap);

            File f1= new File(repertoire);
            if(!f1.exists()){
                f1.mkdirs();
            }

            String message=hashMap.get("id").toString()+".json";
            File pathMess= new File(new File(f1.getAbsolutePath()).toString() + File.separator + message);


            try {

                     //message=hashMap.get("id").toString()+".json";
                    // pathMess= new File(pathMess.toString()+ File.separator + message);


                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathMess));
                    writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
                    writer.close();



            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    /**
     * Getting a {@link File} object for one sender at an according index
     *
     * @param senderMail Sender mail
     * @param index Theincremental index which is optionnal
     * @param filename The file name
     * @return a {@link File} Object
     */
    private File getFile(String senderMail, int index, String filename) {
        return new File(outputDirectory.getAbsolutePath() + File.separator + senderMail.toLowerCase() +
                ( File.separator + index ) + File.separator + filename);

    }

    private static String recuperationEmetteur(HashMap hashMap){
        String emetteur= hashMap.get("FROM").toString();

        if (emetteur.contains("<")){
            emetteur.substring(
                    emetteur.indexOf("<")+1,emetteur.indexOf(">") ).
                    replace("@","").
                    replace(".","").
                    replace("-","").replace("+","").replace("=","");

        }else{
           return emetteur.toString().
                    replace("@","").
                    replace(".","").
                    replace("-","").replace("+","").replace("=","");
        }


        return emetteur;
    }
}
