package it.valeriovaudi.onlyoneportal.accountservice.web.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(
        @JsonProperty("given_name")
        String firstName,

        @JsonProperty("family_name")
        String lastName,

        @JsonProperty("birthdate")
        String birthDate,

        @JsonProperty("email")
        String mail,

        @JsonProperty("phone_number")
        String phone) {
}
