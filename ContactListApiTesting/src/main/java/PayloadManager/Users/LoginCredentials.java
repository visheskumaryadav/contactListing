package PayloadManager.Users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LoginCredentials {

   public static Map<String,String> setUp(){
       ObjectMapper objectMapper=new ObjectMapper();
       Map<String, String> loginCredentials = null;
       try {
           loginCredentials = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/main/java/Resources/LoggedInUserData/LoggedInUserCredentials.json")
                   , new TypeReference<Map<String, String>>() {
                   });
       }catch (IOException e){
           e.printStackTrace();
       }
       return loginCredentials;
   }

//    public static void main(String[] args) {
//        System.out.println(LoginCredentials.setUp());
//    }
}
