package PayloadManager.Users;

import Utils.ExcelUtils;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
This  class is used to generate the payload
 */


public class UserDataGenerator {

    // This is for single data generation for smoke testing
    public static UserPojo addUserPayload(){
        Faker faker=new Faker();
        return new UserPojo().toBuilder()
                .firstName(faker.name().firstName()).lastName(faker.name().lastName())
                .email(faker.text().text(4)+ "@yopmail.com")
                .password(faker.text().text(8)).build();
    }

    // This is for data driven testing
    public static List<UserPojo> addUserPayloadForDDT(){
        // passing the filepath of excel test data
        List<Map<String, String>> data = ExcelUtils.getData("\\src\\test\\TestData\\registerUserTestData.xlsx");
        // stores list of users
        List<UserPojo> users=new ArrayList<>();
        for(Map<String,String> d:data){
            UserPojo user=new UserPojo();

            if(!d.get("first_name").equalsIgnoreCase("NO_DATA"))
                user.setFirstName(d.get("first_name"));
            if(!d.get("last_name").equalsIgnoreCase("NO_DATA"))
                user.setLastName(d.get("last_name"));
            if(!d.get("email").equalsIgnoreCase("NO_DATA"))
                user.setEmail(d.get("email"));
            if(!d.get("password").equalsIgnoreCase("NO_DATA"))
                user.setPassword(d.get("password"));

            if(!d.get("TestID").equalsIgnoreCase("NO_DATA"))
                user.setTestID(Integer.parseInt(d.get("TestID")));
            if(!d.get("TestDescription").equalsIgnoreCase("NO_DATA"))
                user.setTestDescription(d.get("TestDescription"));
            if(!d.get("StatusCode").equalsIgnoreCase("NO_DATA"))
                user.setStatusCode(Integer.parseInt(d.get("StatusCode")));
            if(!d.get("ExpectedErrorMessage").equalsIgnoreCase("NO_DATA"))
                user.setExpectedErrorMessage(d.get("ExpectedErrorMessage"));
            users.add(user);
        }
        return users;

    }
}
