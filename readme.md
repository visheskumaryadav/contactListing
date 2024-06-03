# Contact List APIs

<p>
  <span>APIs are available at this </span>
  <a href="https://documenter.getpostman.com/view/4012288/TzK2bEa8#3c540b2f-92ef-472a-ba77-33179fecd69b">link</a>.
</p>

## Tools/Tech Used:

- Programming language: Java  
- Library: Rest Assured
- Build Tool: Maven

## About the Project:

So here we have 2 kinds of collections: Users and Contacts.

<div style="display: flex; align-items: center;">
  <div style="flex: 0 0 auto;">
    <img src="./readme images/collections.png" alt="Collections Image" style="width: 150px; margin-right: 20px;">
  </div>
  <div style="flex: 1;">
    <p>
      - Basically I tried to automate these APIs using RestAssured.<br>
      - For both modules, I have created a simple smoke test and for the User module, data-driven testing using Excel.<br>
      - My folder structure is broadly divided into src/main/java and src/test/java.<br>
      - In src/main/java, I have defined my request composition support like defining endpoints, payloads, request managers, data generators, and utilities.<br>
      - In src/test/java, I have grouped the test cases into contacts and user packages. In each package, I have created smoke tests and for the user package, I have created a data-driven testing file too.<br>
      - In Assertions, assert utilities are created so that each request can be asserted.
    </p>
  </div>
</div>

## Folder Structure
```
ContactListApiTesting
    ├───.idea
    │   └───inspectionProfiles
    ├───report
    └───src
        ├───main
        │   └───java
        │       ├───EndPointsManager
        │       ├───Listeners
        │       ├───PayloadManager
        │       │   ├───Contacts
        │       │   └───Users
        │       ├───RequestManager
        │       │   ├───Contacts
        │       │   └───Users
        │       ├───Resources
        │       │   ├───LoggedInUserData
        │       │   └───PayloadData
        │       └───Utils
        └───test
            ├───java
            │   ├───Assertions
            │   ├───Contacts
            │   └───Users
            │       └───DataDrivenTesting
            └───TestData

```

### src/main/java

1. **EndPointsManager**:
   - In this package, we have an enum file named "EndPoint" and a "SetUp". By combining these two files, I am able to set up my endpoints.
     <img src="./readme images/Endpoint.png">
     <img src="./readme images/setup.png">

2. **Listeners**:
   - Here I have defined the listener file for reporting.

3. **Payload Manager**:
   - For both Contact APIs and User APIs, I have created POJO classes, data generator classes, and a base POJO which holds info like testID, testInfo, etc.
     <img src="./readme images/basepojo.png">
     <img src="./readme images/contactpojo.png">
     <img src="./readme images/userpojo.png"> 
     <img src="./readme images/userGenerator.png">
     <img src="./readme images/contactgenerator.png"> 

4. **Request Manager**:
   - The purpose of this package is to compose the request using endpoints, headers, and payload.
     <img src="./readme images/contactsendapi.png">
     <img src="./readme images/userapirequest.png"> 

5. **Resources**:
   - Here, only login credentials are present in JSON files.

6. **Utils**:
   - This is a utility package containing code related to Excel file handling and extent report management.
     <img src="./readme images/excelUtil.png">
     <img src="./readme images/extentreportutil.png"> 

### src/test/java

1. **Assertions**:
   - It contains assertion utility where we assert the response with the payload of the request.
     <img src="./readme images/assertionUtills.png">

2. **Contacts**:
   - Here I have prepared a smoke test suite for Contact APIs.
     <img src="./readme images/contactsmoke.png">

3. **Users**:
   - Here I have prepared a smoke test suite for Users APIs and a data-driven test file for register functionality.
     <img src="./readme images/usersmoke.png">
     <img src="./readme images/DDT.png">

4. **TestData**:
   - In this folder, we have our Excel file containing data for data-driven testing.
     <img src="./readme images/exceldata.png">

### testNG.xml file

<img src="./readme images/testng.png">

## Flow
#### Compose the Request - Assert the Respone
- When running the test using Maven, our test cases start to execute in sequence as per the `testng.xml` file.
- First, we need to create a payload. For that, we have a data generator which works using the Faker library. Then we share this payload with other API requests using `ITestContext`.
- After this, we send the request using this payload and a header map.
- Once we get the response, we assert it using an assertion utility which checks the response against the payload.

#### Example

```java
@Test(priority = 1)
public void testAddUserApi(ITestContext context) throws JsonProcessingException {
    // Getting the payload for the request
    UserPojo payload = UserDataGenerator.addUserPayload();

    // Setting the context so that the payload could be shared with other requests
    context.setAttribute("payload", payload);

    // Sending the request
    Response response = UserSendRequest.toAddUser(payload, new LinkedHashMap<>());
    System.out.println("-----------------TEST1 RESPONSE-------------------");

    // Method for performing the assertion
    Assert.assertEquals(response.statusCode(), 201, "Status code is not correct");
    AssertUtils.assertForAddUser(response, payload);

    // Adding the token into the context to make it available to other requests
    context.setAttribute("token", response.body().jsonPath().getString("token"));
}
```

#### Assertion of Response with Payload

The following code is used to assert the API response against the expected payload. This is done to ensure that the response from the API matches the data we sent in the request. Below is the implementation of `AssertUtils` class that provides utility methods for performing these assertions:

```java
package Assertions;

import PayloadManager.Contacts.ContactPojo;
import PayloadManager.Users.UserPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class AssertUtils {

    public static void assertForAddUser(Response response, UserPojo payload) {
        // Converting the response into a Map so that we can do operation using key:value pairs
        Map<String, Object> responseMap = response.as(new TypeRef<LinkedHashMap<String, Object>>() {});
        Assert.assertTrue(responseMap.containsKey("user"), "User property is absent in response");
        Assert.assertTrue(responseMap.containsKey("token"), "Token property is absent in response");

        // Getting the nested JSON object in "user" key and then converting it into a map
        // 'if' conditional is used to avoid any casting exception
        if (responseMap.get("user") instanceof Map) {
            Map<String, Object> userDataInResponse = (Map<String, Object>) responseMap.get("user");
            Assert.assertEquals(userDataInResponse.get("firstName"), payload.getFirstName(), "First name is not matching");
            Assert.assertEquals(userDataInResponse.get("lastName"), payload.getLastName(), "Last name is not matching");
            Assert.assertEquals(userDataInResponse.get("email"), payload.getEmail(), "Email is not matching");
            Assert.assertTrue(userDataInResponse.containsKey("_id"), "ID is absent");
        } else {
            System.out.println("The object is not a Map.");
        }
    }

    public static void assertForLogin(Response response, UserPojo payload) {
        assertForAddUser(response, payload);
    }

    public static void assertForAddUserError(Response response, UserPojo payload) {
        Assert.assertEquals(response.statusCode(), 400, "Status code is not 400");
        // Converting the response into string
        String responseText = response.getBody().asString();
        // Needed to replace the "_pass" word in payload w.r.t to test data
        if (payload.getExpectedErrorMessage().contains("_pass")) {
            payload.setExpectedErrorMessage(payload.getExpectedErrorMessage().replace("_pass", payload.getPassword()));
        }
        Assert.assertTrue(responseText.contains(payload.getExpectedErrorMessage()), "Error message is not correct");
    }

    public static void assertForAddContact(ContactPojo payload, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ContactPojo responsePojo = mapper.readValue(response.getBody().asString(), ContactPojo.class);
            // Assertion is done based on hash and equals method as we are comparing two objects of the same POJO class.
            Assert.assertEquals(responsePojo, payload, "Payload and response are not the same");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
```

#### Explanation

#### assertForAddUser:
- Converts the response to a `Map` for key-value pair operations.
- Checks if the response contains keys `user` and `token`.
- Extracts the nested `user` object and asserts individual fields against the payload.

#### assertForLogin:
- Simply reuses `assertForAddUser` to perform the same assertions.

#### assertForAddUserError:
- Asserts that the status code is `400`.
- Converts the response body to a `String` and checks if it contains the expected error message.

#### assertForAddContact:
- Converts the response body to a `ContactPojo` object using `ObjectMapper`.
- Asserts that the response POJO equals the payload POJO based on the overridden `equals` method.

### Data Driven Testing

The following method is used for data driven testing. It reads user data from an Excel file and creates a list of `UserPojo` objects based on that data.

### Code

```java
public static List<UserPojo> addUserPayloadForDDT() {
    // Passing the filepath of the Excel test data
    List<Map<String, String>> data = ExcelUtils.getData("\\src\\test\\TestData\\registerUserTestData.xlsx");
    // Stores list of users
    List<UserPojo> users = new ArrayList<>();
    
    for (Map<String, String> d : data) {
        UserPojo user = new UserPojo();

        if (!d.get("first_name").equalsIgnoreCase("NO_DATA"))
            user.setFirstName(d.get("first_name"));
        if (!d.get("last_name").equalsIgnoreCase("NO_DATA"))
            user.setLastName(d.get("last_name"));
        if (!d.get("email").equalsIgnoreCase("NO_DATA"))
            user.setEmail(d.get("email"));
        if (!d.get("password").equalsIgnoreCase("NO_DATA"))
            user.setPassword(d.get("password"));

        if (!d.get("TestID").equalsIgnoreCase("NO_DATA"))
            user.setTestID(Integer.parseInt(d.get("TestID")));
        if (!d.get("TestDescription").equalsIgnoreCase("NO_DATA"))
            user.setTestDescription(d.get("TestDescription"));
        if (!d.get("StatusCode").equalsIgnoreCase("NO_DATA"))
            user.setStatusCode(Integer.parseInt(d.get("StatusCode")));
        if (!d.get("ExpectedErrorMessage").equalsIgnoreCase("NO_DATA"))
            user.setExpectedErrorMessage(d.get("ExpectedErrorMessage"));

        users.add(user);
    }
    return users;
}
```
### Explanation

#### Data Retrieval:
- The method `addUserPayloadForDDT` fetches test data from an Excel file located at `\\src\\test\\TestData\\registerUserTestData.xlsx`.

#### List Initialization:
- Initializes an empty list of `UserPojo` objects to store user data.

#### Data Parsing:
- Iterates through each row of data in the Excel file.
- Creates a new `UserPojo` object for each row of data.

#### Conditional Data Assignment:
- Assigns values to the `UserPojo` object fields only if the corresponding cell in the Excel file does not contain the string "NO_DATA".
- Fields include `firstName`, `lastName`, `email`, `password`, `TestID`, `TestDescription`, `StatusCode`, and `ExpectedErrorMessage`.

#### Adding Users to List:
- Adds each populated `UserPojo` object to the list of users.

#### Return Statement:
- Returns the list of `UserPojo` objects.

