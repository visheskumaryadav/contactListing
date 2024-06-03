package Contacts;

import Assertions.AssertUtils;
import PayloadManager.Contacts.ContactDataGenerator;
import PayloadManager.Contacts.ContactPojo;
import PayloadManager.Users.UserDataGenerator;
import PayloadManager.Users.UserPojo;
import RequestManager.Contacts.ContactSendRequest;
import RequestManager.Users.UserSendRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;
// testcases using testNG for contacts
public class SmokeTest {
    private String token;
    private  UserPojo registerUser(){
        // Getting the payload for the request
        UserPojo payload= UserDataGenerator.addUserPayload();
        //Sending the request
        Response response= UserSendRequest.toAddUser(payload,new LinkedHashMap<>());
        System.out.println("-----------------Register RESPONSE-------------------");
        //Method for performing the assertion
        Assert.assertEquals(response.statusCode(),201,"Status code is not correct");
        AssertUtils.assertForAddUser(response,payload);
        return payload;
    }
    private String loginTheUser(UserPojo payload){
        Map<String,String> credentials=new LinkedHashMap<>();
        credentials.put("email",payload.getEmail());
        credentials.put("password",payload.getPassword());
        Response response= UserSendRequest.toLoginUser(credentials);
        System.out.println("-----------------Login RESPONSE-------------------");
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200,"Status code is not correct");

        AssertUtils.assertForLogin(response,payload);
        token=response.body().jsonPath().getString("token");
        return token;
    }

    private int logoutUser(String token){
        //Map for storing the token
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + token);
        Response response= UserSendRequest.toLogoutUser(header);
        System.out.println("-----------------TEST3 RESPONSE-------------------");
        //Performing assertions
        response.then().log().all().assertThat().statusCode(200);
        Assert.assertTrue(response.getBody().asString().isEmpty(),"Body is not empty");
        return response.statusCode();

    }
    @BeforeClass
    public void generateToken(ITestContext context){
        context.setAttribute("token",loginTheUser(registerUser()));
       }

    @Test(priority = 1)
    public void testAddContactApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        ContactPojo payLoad=ContactDataGenerator.getContactPayLoad();
        System.out.println(payLoad.toString());
        Response response=ContactSendRequest.toAddContact(payLoad,header);
        response.then().log().all();
        if(response.statusCode()==201){
            AssertUtils.assertForAddContact(payLoad,response);
            context.setAttribute("contactID",response.getBody().jsonPath().getString("_id"));
        }else{
            Assert.assertEquals(response.statusCode(), 201, "Status code is not 201");
        }
    }

    @Test(priority = 2)
    public void testGetContactListApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response=ContactSendRequest.toGetContactList(header);
        response.then().log().all();

    }
    @Test(priority = 3)
    public void testGetContactApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response=ContactSendRequest.toGetContact(header,(String)context.getAttribute("contactID"));
        response.then().log().all();

    }
    @Test(priority = 4)
    public void testUpdateContactApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        ContactPojo updatePayLoad =ContactDataGenerator.getContactPayLoad();
        Response response=ContactSendRequest.toUpdateContact(updatePayLoad,header,(String)context.getAttribute("contactID"));
        response.then().log().all();

    }
    @Test(priority = 5)
    public void testDeleteContactApi(ITestContext context){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + context.getAttribute("token"));
        Response response=ContactSendRequest.toDeleteContact(header,(String)context.getAttribute("contactID"));
        context.removeAttribute("contactID");
        response.then().log().all();

    }
    @AfterClass
    public void deleteUser(){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + token);
        Response response= UserSendRequest.toDeleteUser(header);
        System.out.println("-----------------DeleteUser RESPONSE-------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertTrue(response.getBody().asString().isEmpty(),"Body is not empty");
    }
}
