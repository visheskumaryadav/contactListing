package PayloadManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

// This is class for Data driven testing where i need to fetch data row by row and these are the columns where describe about
// what kind of testcase it so that we can  assert it

@Data
public class BasePojo {
    @JsonIgnore
    private int testID;
    @JsonIgnore
    private String testDescription;
    @JsonIgnore
    private int statusCode;
    @JsonIgnore
    private String expectedErrorMessage;

}
