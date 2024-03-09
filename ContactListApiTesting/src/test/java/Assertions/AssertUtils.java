package Assertions;

import PayloadManager.Users.UserPojo;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class AssertUtils {

    public static void assertForAddUser(Response response, UserPojo payload){

        Assert.assertEquals(response.statusCode(),201,"Status code is not correct");
        //converting the response into a Map so that we can do operation using key:value pairs
        Map<String,Object> responseMap=response.as(new TypeRef<LinkedHashMap<String, Object>>() {});
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
    }

    public static void assertForLogin(Response response,UserPojo payload){
        assertForAddUser(response,payload);
    }

    public static void assertForAddUserError(Response response,UserPojo payload){
        Assert.assertEquals(response.statusCode(), 400, "Status code is not 400");
        // converting the response into string
        String responseText=response.getBody().asString();
        // Needed to replace the "_pass" word in payload w.r.t to testdata
        if(payload.getExpectedErrorMessage().contains("_pass"))
            payload.setExpectedErrorMessage(payload.getExpectedErrorMessage().replace("_pass",payload.getPassword()));
        Assert.assertTrue(responseText.contains(payload.getExpectedErrorMessage()),"error message is not correct");
    }



}
