package solutions.wo.it.data.ofx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OFXDataHelper {

    private OFXDataHelper() {}

    public static LocalDateTime getDate(String date) {
        String inputWithoutTimeZone = date.replaceAll("\\[.*\\]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        try {
            return LocalDateTime.parse(inputWithoutTimeZone, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
