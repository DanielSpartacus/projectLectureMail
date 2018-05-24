package constants;

import org.apache.commons.lang3.StringUtils;

public enum     TypeMailConstant {;

     public static String MULTIPART_ALL = "multipart/*";

    public static String MULTIPART_ALTERNATIVE = "multipart/alternative";

     public static String TEXT_ALL = "text/*";

     public static String TEXT_PLAIN = "text/plain";

    public static String TEXT_HTML = "text/html";

    public  static String MESSAGE_RFC822 = "message/rfc822";

    public static String IMAGE_JPEG ="image/jpeg";

    private  String code="";

    private TypeMailConstant(String text){
        this.code=text;
    }
    public static String  TypeMailConstant_Byvalue(String code){
        for (final TypeMailConstant en: values()
                ) {
            if (en.code.equals(code)){
                return code;
            }

        }
        return null;
    }
    public String getCode() {
        return code;
    }
}
