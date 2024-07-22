import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MovieLicenseDTO {
    private Long movieId;
    private String mainTitle;
    private String type;
    private String providerName;
    private String currentReleaseDate;
    private String currentExpiryDate;
    private String currentStatus;
    private List<LicenseHistoryEntry> licenseHistory;
}

@Data
@Builder
public class LicenseHistoryEntry {
    private Long movieId;
    private String mainTitle;
    private String type;
    private String providerName;
    private String historicalReleaseDate;
    private String historicalExpiryDate;
    private String historicalStatus;
    private String currentReleaseDate;
    private String currentExpiryDate;
    private LocalDateTime changeDate;
}

@Data
@Builder
public class LicenseUpdateRequest {
    private Long movieId;
    private String newReleaseDate;
    private String newExpiryDate;
}
