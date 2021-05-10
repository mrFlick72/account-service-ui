package it.valeriovaudi.onlyoneportal.accountservice.web.representation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Account {

    private String firstName;
    private String lastName;

    private String birthDate;

    private String mail;

    private String phone;

    public Account() { }

    public Account(String firstName, String lastName, String birthDate, String mail, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.mail = mail;
        this.phone = phone;
    }
}
