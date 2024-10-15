import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class LicenseWindowCalculator {

    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId INDIA_ZONE = ZoneId.of("Asia/Kolkata");

    private static final long EXPIRING_THRESHOLD = 30;
    private static final long UPCOMING_THRESHOLD = 300;

    public static String calculateLicenseWindow(String startDateStr, String endDateStr) {
        LocalDate now = LocalDate.now(INDIA_ZONE);
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);

        if (startDate == null) {
            return "";
        }

        if (endDate == null) {
            endDate = now.plus(1000, ChronoUnit.YEARS);
        }

        return determineLicenseStatus(now, startDate, endDate);
    }

    private static String determineLicenseStatus(LocalDate now, LocalDate startDate, LocalDate endDate) {
        if (now.isBefore(startDate)) {
            return getUpcomingStatus(ChronoUnit.DAYS.between(now, startDate));
        }
        
        if (now.isAfter(endDate)) {
            return "Expired";
        }
        
        long daysUntilEnd = ChronoUnit.DAYS.between(now, endDate);
        if (daysUntilEnd <= EXPIRING_THRESHOLD) {
            return formatDays("Expiring in %d day%s", daysUntilEnd);
        }
        
        return "Active";
    }

    private static String getUpcomingStatus(long daysUntilStart) {
        if (daysUntilStart > UPCOMING_THRESHOLD) {
            return "Upcoming after 300 days";
        }
        return formatDays("Upcoming in %d day%s", daysUntilStart);
    }

    private static String formatDays(String format, long days) {
        return String.format(format, days, days == 1 ? "" : "s");
    }

    private static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return Instant.parse(dateStr).atZone(UTC).toLocalDate();
    }
}