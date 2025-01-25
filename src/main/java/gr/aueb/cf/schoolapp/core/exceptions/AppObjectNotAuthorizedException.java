package gr.aueb.cf.schoolapp.core.exceptions;

public class AppObjectNotAuthorizedException extends AppGenericException {
    public static final String DEFAULT_CODE = "Not Authorized";

    public AppObjectNotAuthorizedException(String code, String message) {

        super(code + DEFAULT_CODE, message);
    }
}
