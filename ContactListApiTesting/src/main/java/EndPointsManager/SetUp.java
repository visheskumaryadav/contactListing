package EndPointsManager;

/*
This "SetUp" class is used for setting up the endpoints so that we can have complete URLs for our request.
 */
public class SetUp {

    public static String endpoint(EndPoint endPointName){
        String baseUrl="https://thinking-tester-contact-list.herokuapp.com";

        return switch (endPointName) {
            case ADD_CONTACT,GET_CONTACT_LIST -> baseUrl + "/contacts";
            case GET_CONTACT,UPDATE_CONTACT,DELETE_CONTACT -> baseUrl+"/contacts/{contactId}";
            case ADD_USER ->baseUrl+"/users";
            case LOGIN_USER -> baseUrl +"/users/login";
            case LOGOUT_USER -> baseUrl + "/users/logout";
            case GET_USER_PROFILE,DELETE_USER -> baseUrl +"/users/me";
            default -> throw new IllegalArgumentException("Invalid path: " + endPointName);
        };
    }

}
