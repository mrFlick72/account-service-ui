package it.valeriovaudi.onlyoneportal.accountservice.web.representation;


public record Account(String firstName,
                      String lastName,
                      String birthDate,
                      String mail,
                      String phone) {
}
