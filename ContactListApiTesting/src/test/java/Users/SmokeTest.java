package Users;

import PayloadManager.Users.UserDataGenerator;
import PayloadManager.Users.UserPojo;
import RequestManager.Users.SendRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SmokeTest {


    
    @Test(priority = 1)
    public void testAddUserApi(ITestContext context) throws JsonProcessingException {
        // Getting the payload for the request
        UserPojo payload=UserDataGenerator.addUserPayload();
        // Setting the context so that payload could be shared with other requests
        context.setAttribute("payload",payload);
        //Sending the request
        Response response= SendRequest.toAddUser(payload,new LinkedHashMap<>());
        System.out.println("-----------------TEST1 RESPONSE-------------------");
        // Response Assertion
        Assert.assertEquals(response.statusCode(),201,"Status code is not correct");
        //converting the response into a Map so that we can do operation using key:value pairs
        Map <String,Object> responseMap=response.as(new TypeRef<LinkedHashMap<String, Object>>() {});
        Assert.assertTrue(responseMap.containsKey("user"),"User property is absent in response");
        Assert.assertTrue(responseMap.containsKey("token"),"token property is absent in response");
        // getting the nested json object in "user" key and then converting it into a map
        // if conditional is used to avoid any casting exception
        if(responseMap.get("user") instanceof Map){
              Map<String, Object> userDataInResponse = (Map<String,Object>) responseMap.get("user");
              Assert.assertEquals(userDataInResponse.get("firstName"),payload.getFirstName(),"firstName is not matching");
              Assert.assertEquals(userDataInResponse.get("lastName"),payload.getLastName(),"lastname is not matching");
              Assert.assertEquals(userDataInResponse.get("email"),payload.getEmail() ,"email is not matching");
              Assert.assertTrue(userDataInResponse.containsKey("_id"),"ID is absent");
        }else{
              System.out.println("The object is not a Map.");
        }
        // adding the token into the context to make it available to other requests
        context.setAttribute("token",response.body().jsonPath().getString("token"));

//          ObjectMapper objectMapper=new ObjectMapper();
//          UserPojo user=objectMapper.readValue(responseMap.get("user").toString(),UserPojo.class);
//
//          Assert.assertEquals(user.getFirstName() , payload.getFirstName());
//          Assert.assertEquals(user.getLastName(),payload.getLastName());
//          Assert.assertEquals(user.getEmail(),payload.getEmail());


    }

    @Test(priority = 2)
    public void testGetUserProfileApi(ITestContext context) throws JsonProcessingException {
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response=SendRequest.toGetUserProfile(header);
        System.out.println("-----------------TEST2 RESPONSE-------------------");
        response.then().log().all();

        ObjectMapper objectMapper=new ObjectMapper();
        UserPojo user=objectMapper.readValue(response.toString(),UserPojo.class);
        UserPojo payload= (UserPojo) context.getAttribute("payload");
        Assert.assertEquals(user.getFirstName() ,payload.getFirstName(),"firstName is not matching");
        Assert.assertEquals(user.getLastName(),payload.getLastName(),"lastName is not matching");
        Assert.assertEquals(user.getEmail(),payload.getEmail(),"email is not matching");
        Assert.assertFalse(response.jsonPath().getString("_id").isEmpty(),"ID is empty");

    }

    @Test(priority = 3)
    public void testLogoutUserApi(ITestContext context){
        context.getAttribute("token");
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response=SendRequest.toLogoutUser(header);
        System.out.println("-----------------TEST3 RESPONSE-------------------");
        response.then().log().all();
        
        context.removeAttribute("token");

    }
//
//    @Test(priority = 4)
//    public void testLoginUserApi(ITestContext context){
//        UserPojo payload=(UserPojo)context.getAttribute("payload");
//        Map <String,String> credentials=new LinkedHashMap<>();
//        credentials.put("email",payload.getEmail());
//        credentials.put("password",payload.getPassword());
//
//        Response response=SendRequest.toLoginUser(credentials);
//        System.out.println("-----------------TEST4 RESPONSE-------------------");
//        response.then().log().all();
//        context.setAttribute("token",response.body().jsonPath().getString("token"));
//    }
//
//    @Test(priority = 5)
//    public void testDeleteUserApi(ITestContext context){
//        Map<String,String> header=new LinkedHashMap<>();
//        header.put("Authorization", "Bearer " + context.getAttribute("token"));
//
//        Response response=SendRequest.toDeleteUser(header);
//        System.out.println("-----------------TEST5 RESPONSE-------------------");
//        response.then().log().all();
//        context.removeAttribute("token");
//    }
//    @Test(priority = 6)
//    public void testIsUserDeleted(ITestContext context){
//        UserPojo payload=(UserPojo)context.getAttribute("payload");
//        Map <String,String> credentials=new LinkedHashMap<>();
//        credentials.put("email",payload.getEmail());
//        credentials.put("password",payload.getPassword());
//
//        Response response=SendRequest.toLoginUser(credentials);
//        System.out.println("-----------------TEST6 RESPONSE-------------------");
//        response.then().log().all();
//    }
}
