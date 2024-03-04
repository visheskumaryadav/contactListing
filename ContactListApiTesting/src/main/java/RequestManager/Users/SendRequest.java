package RequestManager.Users;

import EndPointsManager.EndPoint;
import EndPointsManager.SetUp;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;
/*
This class used to setup to the request so that we can send it
 */
public class SendRequest {


    public static Response toAddUser(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers).log().all()
                .body(payload)
                .post(SetUp.endpoint(EndPoint.ADD_USER));
    }
    public static Response toLoginUser(Object payload){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(payload)
                .post(SetUp.endpoint(EndPoint.LOGIN_USER));
    }
    public static Response toLogoutUser(Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .post(SetUp.endpoint(EndPoint.LOGOUT_USER));
    }

    public static Response toGetUserProfile(Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .get(SetUp.endpoint(EndPoint.GET_USER_PROFILE));
    }

    public static Response toDeleteUser(Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .delete(SetUp.endpoint(EndPoint.DELETE_USER));
    }


}
