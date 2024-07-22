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
    private String releaseDate;
    private String expiryDate;
    private String status;
    private LocalDateTime changeDate;
}

@Data
@Builder
public class LicenseHistoryRequest {
    private Long movieId;
    private String oldReleaseDate;
    private String oldExpiryDate;
    private String oldStatus;
}
