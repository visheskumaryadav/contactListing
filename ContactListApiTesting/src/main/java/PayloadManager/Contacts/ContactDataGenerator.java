package PayloadManager.Contacts;

import net.datafaker.Faker;

public class ContactDataGenerator {


    public static  ContactPojo getContactPayLoad(){
        Faker fake=new Faker();
        return new ContactPojo().toBuilder()
                .firstName(fake.name().firstName()).lastName(fake.name().lastName())
                .birthdate(fake.date().birthday("yyyy-mm-dd"))
                .email(fake.text().text(5)+"yopmail.com")
                .phone(fake.phoneNumber().cellPhone())
                .street1(fake.address().streetAddressNumber()).street2(fake.address().streetAddress())
                .city(fake.address().city()).country(fake.country().countryCode2()).postalCode(fake.address().postcode())
                .stateProvince(fake.address().state()).build();
    }
}
