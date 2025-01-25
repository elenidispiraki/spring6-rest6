package gr.aueb.cf.schoolapp.core.exceptions;

public class AppObjectNotFoundException extends AppGenericException {

  public static final String DEFAULT_CODE = "Not Found";

    public AppObjectNotFoundException(String code, String message) {

      super(code + DEFAULT_CODE, message);
    }
}
