package it.valeriovaudi.onlyoneportal.accountservice.adapters.date;

import java.time.format.DateTimeFormatter;

public class Dates {
    private Dates() {}

    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter UI_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
