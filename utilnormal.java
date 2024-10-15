import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class LicenseWindowCalculator {

    public static String calculateLicenseWindow(String startDateStr, String endDateStr) {
        Instant now = Instant.now();
        Instant startDate = parseDate(startDateStr);
        Instant endDate = parseDate(endDateStr);

        if (startDate == null) {
            return "";
        }

        if (endDate == null) {
            endDate = now.plus(1000, ChronoUnit.YEARS); // Treat as far future
        }

        long daysUntilStart = ChronoUnit.DAYS.between(now, startDate);
        if (daysUntilStart > 0) {
            if (daysUntilStart > 300) {
                return "Upcoming after 300 days";
            }
            return String.format("Upcoming in %d day%s", daysUntilStart, daysUntilStart == 1 ? "" : "s");
        }

        if (now.isAfter(endDate)) {
            return "Expired";
        }

        long daysUntilEnd = ChronoUnit.DAYS.between(now, endDate);
        if (daysUntilEnd <= 30) {
            return String.format("Expiring in %d day%s", daysUntilEnd, daysUntilEnd == 1 ? "" : "s");
        }

        return "Active";
    }

    private static Instant parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return Instant.parse(dateStr);
    }
}