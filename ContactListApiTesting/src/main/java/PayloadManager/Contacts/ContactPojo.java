package PayloadManager.Contacts;

import PayloadManager.BasePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public class ContactPojo extends BasePojo {
//    @JsonProperty("firstName")
    private String firstName;

//    @JsonProperty("lastName")
    private String lastName;

//    @JsonProperty("birthdate")
    private String birthdate;

//    @JsonProperty("email")
    private String email;

//    @JsonProperty("phone")
    private String phone;

//    @JsonProperty("street1")
    private String street1;

//    @JsonProperty("street2")
    private String street2;

//    @JsonProperty("city")
    private String city;

//    @JsonProperty("stateProvince")
    private String stateProvince;

//    @JsonProperty("postalCode")
    private String postalCode;

//    @JsonProperty("country")
    private String country;

    @Override
    public String toString() {
        return "ContactPojo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street1='" + street1 + '\'' +
                ", street2='" + street2 + '\'' +
                ", city='" + city + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
