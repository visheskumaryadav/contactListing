package PayloadManager.Users;

import net.datafaker.Faker;

public class UserDataGenerator {

    public static UserPojo addUserPayload(){
        Faker faker=new Faker();
        return new UserPojo().toBuilder()
                .firstName(faker.name().firstName()).lastName(faker.name().lastName())
                .email(faker.text().text(4)+ "@yopmail.com")
                .password(faker.text().text(8)).build();
    }
}
