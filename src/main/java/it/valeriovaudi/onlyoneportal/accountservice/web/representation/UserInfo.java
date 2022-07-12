package it.valeriovaudi.onlyoneportal.accountservice.web.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class UserInfo {

    @JsonProperty("given_name")
    private String firstName;

    @JsonProperty("family_name")
    private String lastName;

    @JsonProperty("birthdate")
    private String birthDate;

    @JsonProperty("email")
    private String mail;

    @JsonProperty("phone_number")
    private String phone;

    public UserInfo() { }

    public UserInfo(String firstName, String lastName, String birthDate, String mail, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.mail = mail;
        this.phone = phone;
    }
}
