package RequestManager.Contacts;

import EndPointsManager.EndPoint;
import EndPointsManager.SetUp;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;
// Here we compose request for the contact APIs
public class ContactSendRequest {


    public static Response toAddContact(Object payload, Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .body(payload)
                .post(SetUp.endpoint(EndPoint.ADD_CONTACT));
    }
    public static Response toGetContactList( Map<String,String> headers){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .get(SetUp.endpoint(EndPoint.GET_CONTACT_LIST));
    }
    public static Response toGetContact(Map<String,String> headers,String contactId){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .pathParam("contactId",contactId)
                .get(SetUp.endpoint(EndPoint.GET_CONTACT));
    }
    public static Response toUpdateContact(Object payload, Map<String,String> headers,String contactId){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .pathParam("contactId",contactId)
                .body(payload)
                .put(SetUp.endpoint(EndPoint.UPDATE_CONTACT));
    }
    public static Response toDeleteContact(Map<String,String> headers,String contactId){
        return RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON).headers(headers)
                .pathParam("contactId",contactId)
                .delete(SetUp.endpoint(EndPoint.DELETE_CONTACT));
    }


}
