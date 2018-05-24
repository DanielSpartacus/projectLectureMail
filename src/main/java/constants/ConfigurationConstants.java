package constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface ConfigurationConstants {
    /**
     * Application parameters
     */
    public static String MAIL_DEBUG_ENABLE = "mail.debug.enable";
    public static String MAIL_OUTPUT_PATH = "mail.output.path";
    public static String MAIL_OUTPUT_PATH_DEFAULT = "output";

    /**
     * Protocole parameters
     */
    public static String MAIL_STORE_PROTOCOL = "mail.store.protocol";
    public static String MAIL_STORE_PROTOCOL_IMAP = "imap";
    public static String MAIL_STORE_PROTOCOL_POP3 = "pop3";
    public static String MAIL_STORE_PROTOCOL_MICROSOFT_EWS = "ews";
    public static String MAIL_STORE_PROTOCOL_DEFAULT_VALUE = MAIL_STORE_PROTOCOL_POP3;
    public static List<String> HANDLED_PROTOTOLS = Collections.unmodifiableList(Arrays.asList(MAIL_STORE_PROTOCOL_IMAP,
            MAIL_STORE_PROTOCOL_POP3,
            MAIL_STORE_PROTOCOL_MICROSOFT_EWS));

    /**
     * Mail server configuration properties
     */
    public static String MAIL_HOST = "mail.host";
    public static String MAIL_HOST_POP3 = "mail.pop3.host";
    public static String MAIL_POP3_PORT = "mail.pop3.port";


    public static String MAIL_USER = "mail.user";
    public static String MAIL_PASSWORD = "mail.password";
    public static String MAIL_ROOT_DIRECTORY_NAME = "INBOX";
    public static String HTTPS_PREFIX = "https://";
    public static String MAIL_IMAP_STARTTLS_ENABLE = "mail.imap.starttls.enable";
    public static String MAIL_IMAP_SSL_ENABLE = "mail.imap.ssl.enable";

    /**
     * EWS protocol specific properties
     */
    public static String MAIL_EWS_DOMAIN = "mail.ews.domain";
    public static String MAIL_EWS_EMAIL = "mail.ews.email";



}
