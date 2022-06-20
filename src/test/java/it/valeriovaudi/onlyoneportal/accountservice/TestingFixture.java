package it.valeriovaudi.onlyoneportal.accountservice;


import it.valeriovaudi.onlyoneportal.accountservice.web.representation.Account;
import it.valeriovaudi.onlyoneportal.accountservice.web.representation.UserInfo;

import java.util.Map;

public class TestingFixture {


    public static Account anAccount() {
        return new Account("FIRST_NAME",
                "LAST_NAME",
                "1970-01-01",
                "user.mail@mail.com",
                ""
        );
    }

    public static String anAccountAsJsonString() {
        return "{\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"birthDate\":\"01/01/1970\",\"mail\":\"user.mail@mail.com\",\"phone\":\"\"}";
    }

    public static Map<String, String> i18nsMessage() {
        return Map.of("key1", "value1");
    }

    public static final String ACCOUNT_MAIL = "user.mail@mail.com";

    public static UserInfo aUserInfo() {
        return new UserInfo("FIRST_NAME",
                "LAST_NAME",
                "1970-01-01",
                "user.mail@mail.com",
                ""
        );

    }
}