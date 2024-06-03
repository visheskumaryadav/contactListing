package Users;

import Assertions.AssertUtils;
import PayloadManager.Users.UserDataGenerator;
import PayloadManager.Users.UserPojo;
import RequestManager.Users.UserSendRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;
// testcase for user apis
public class SmokeTest {


    
    @Test(priority = 1)
    public void testAddUserApi(ITestContext context) throws JsonProcessingException {
        // Getting the payload for the request
        UserPojo payload=UserDataGenerator.addUserPayload();

        // Setting the context so that payload could be shared with other requests
        context.setAttribute("payload",payload);
        //Sending the request
        Response response= UserSendRequest.toAddUser(payload,new LinkedHashMap<>());
        System.out.println("-----------------TEST1 RESPONSE-------------------");
        //Method for performing the assertion
        Assert.assertEquals(response.statusCode(),201,"Status code is not correct");
        AssertUtils.assertForAddUser(response,payload);
        // adding the token into the context to make it available to other requests
        context.setAttribute("token",response.body().jsonPath().getString("token"));
    }

    @Test(priority = 2)
    public void testGetUserProfileApi(ITestContext context) throws JsonProcessingException {
        //Map for storing the token
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response= UserSendRequest.toGetUserProfile(header);
        System.out.println("-----------------TEST2 RESPONSE-------------------");
       // Performing the assertions after deserializing  the response into User pojo class
        ObjectMapper objectMapper=new ObjectMapper();
        UserPojo user=objectMapper.readValue(response.asString(), UserPojo.class);
        UserPojo payload= (UserPojo) context.getAttribute("payload");
        Assert.assertEquals(user.getFirstName() ,payload.getFirstName(),"firstName is not matching");
        Assert.assertEquals(user.getLastName(),payload.getLastName(),"lastName is not matching");
        Assert.assertEquals(user.getEmail(),payload.getEmail(),"email is not matching");
        Assert.assertFalse(response.jsonPath().getString("_id").isEmpty(),"ID is empty");

    }

    @Test(priority = 3)
    public void testLogoutUserApi(ITestContext context){
        //Map for storing the token
        context.getAttribute("token");
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response= UserSendRequest.toLogoutUser(header);
        System.out.println("-----------------TEST3 RESPONSE-------------------");
        //Performing assertions
        response.then().log().all().assertThat().statusCode(200);
        Assert.assertTrue(response.getBody().asString().isEmpty(),"Body is not empty");
        context.removeAttribute("token");

    }

    @Test(priority = 4)
    public void testLoginUserApi(ITestContext context){
        UserPojo payload=(UserPojo)context.getAttribute("payload");
        Map <String,String> credentials=new LinkedHashMap<>();
        credentials.put("email",payload.getEmail());
        credentials.put("password",payload.getPassword());

        Response response= UserSendRequest.toLoginUser(credentials);
        System.out.println("-----------------TEST4 RESPONSE-------------------");
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200,"Status code is not correct");

        AssertUtils.assertForLogin(response,payload);
        context.setAttribute("token",response.body().jsonPath().getString("token"));
    }

    @Test(priority = 5)
    public void testDeleteUserApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));

        Response response= UserSendRequest.toDeleteUser(header);
        System.out.println("-----------------TEST5 RESPONSE-------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertTrue(response.getBody().asString().isEmpty(),"Body is not empty");
        context.removeAttribute("token");
    }
    @Test(priority = 6)
    public void testIsUserDeleted(ITestContext context){
        UserPojo payload=(UserPojo)context.getAttribute("payload");
        Map <String,String> credentials=new LinkedHashMap<>();
        credentials.put("email",payload.getEmail());
        credentials.put("password",payload.getPassword());

        Response response= UserSendRequest.toLoginUser(credentials);
        System.out.println("-----------------TEST6 RESPONSE-------------------");

    }
}
