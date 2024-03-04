package RequestManager.Contacts;

import EndPointsManager.EndPoint;
import EndPointsManager.SetUp;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class SendRequest {


    public static Response toAddUser(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .post(SetUp.endpoint(EndPoint.ADD_USER));
    }
    public static Response toAddContact(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .post(SetUp.endpoint(EndPoint.ADD_CONTACT));
    }
    public static Response toGetContactList(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .get(SetUp.endpoint(EndPoint.GET_CONTACT_LIST));
    }
    public static Response toGetContact(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .get(SetUp.endpoint(EndPoint.GET_CONTACT));
    }
    public static Response toUpdateContact(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .put(SetUp.endpoint(EndPoint.UPDATE_CONTACT));
    }
    public static Response toDeleteContact(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .delete(SetUp.endpoint(EndPoint.DELETE_CONTACT));
    }


}