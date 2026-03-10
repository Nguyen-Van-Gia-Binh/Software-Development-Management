package utilities;

import java.time.format.DateTimeFormatter;

public interface Validator {
    String DEV_ID_REGEX = "^DEV\\d{3}$";
    String FULL_NAME_REGEX = "^[a-zA-Z]+(\\s[a-zA-Z]+)+$";
    String SALARY_REGEX = "^[1-9]\\d{3,}$";
    String DURATION_REGEX = "^[1-9]\\d*$";
    String NON_EMPTY_REGEX = "^.+$";
    String DATE_VALID = "";
    String PROJ_ID_REGEX = "^PROJ\\d{2}$";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static boolean isValid(String input, String regex) {
        return input.matches(regex);
    }
}