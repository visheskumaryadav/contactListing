package Users.DataDrivenTesting;

import Assertions.AssertUtils;
import PayloadManager.Users.UserDataGenerator;
import PayloadManager.Users.UserPojo;
import RequestManager.Users.SendRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterUserApiTest {
// This will provide the data for consecutive requests
    @DataProvider
    public Iterator<UserPojo> addUserPayLoad(){
        return UserDataGenerator.addUserPayloadForDDT().iterator();
    }

    // Sends the add user request
    @Test(dataProvider = "addUserPayLoad")
    public void testAddUserApi(ITestContext context,UserPojo payload) throws JsonProcessingException {
        //Sending the request
        Response response= SendRequest.toAddUser(payload,new LinkedHashMap<>());
        System.out.println("-----------------TEST1 RESPONSE-------------------");
        if(response.statusCode()>=200 && response.statusCode()<=205){
            // assertion for the requests which returns 200 or 201
            AssertUtils.assertForAddUser(response,payload);
            // Deleting the added user
            deleteUserApi(response.body().jsonPath().getString("token"));
        }else{
            // Asserting the response with 400
            AssertUtils.assertForAddUserError(response,payload);
        }
    }
    //Method which deletes the added user
    public void deleteUserApi(String token){
        Map<String,String> header=new LinkedHashMap<>();
        header.put("Authorization", "Bearer " + token);
        Response response=SendRequest.toDeleteUser(header);
        System.out.println("-----------------DELETING USER-------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertTrue(response.getBody().asString().isEmpty(),"Body is not empty");
    }


}
