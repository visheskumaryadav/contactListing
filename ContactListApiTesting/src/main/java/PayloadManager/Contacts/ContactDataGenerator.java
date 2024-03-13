package PayloadManager.Contacts;

import net.datafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactDataGenerator {


    public static  ContactPojo getContactPayLoad(){
        Faker fake=new Faker();
        /*
        if you set the age to 30 and the ageRange to 10, Faker will generate
        birthdates for people whose age is between 30 and 40 (30 + 10).
         */
        Date birthdate = fake.date().birthday(50, 20);
        // Format the birthdate as a string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedBirthdate = sdf.format(birthdate);

        return new ContactPojo().toBuilder()
                .firstName(fake.name().firstName()).lastName(fake.name().lastName())
                .birthdate(formattedBirthdate)
                .email(fake.text().text(5)+"@yopmail.com")
                .phone(fake.phoneNumber().cellPhone().replace(".",""))
                .street1(fake.address().streetAddressNumber()).street2(fake.address().streetAddress())
                .city(fake.address().city()).country(fake.country().countryCode2()).postalCode(fake.address().postcode())
                .stateProvince(fake.address().state()).build();
    }
}
