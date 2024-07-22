import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieLicenseService {
    private final MovieLicenseMapper mapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public MovieLicenseService(MovieLicenseMapper mapper) {
        this.mapper = mapper;
    }

    public List<MovieLicenseDTO> getMovieLicensesWithHistory(int page, int size, String sort) {
        List<LicenseHistoryEntry> entries = mapper.getLicenseHistoryEntries(page, size, sort);
        
        Map<Long, List<LicenseHistoryEntry>> groupedEntries = entries.stream()
            .collect(Collectors.groupingBy(LicenseHistoryEntry::getMovieId));
        
        return groupedEntries.entrySet().stream()
            .map(this::createMovieLicenseDTO)
            .collect(Collectors.toList());
    }

    private MovieLicenseDTO createMovieLicenseDTO(Map.Entry<Long, List<LicenseHistoryEntry>> entry) {
        List<LicenseHistoryEntry> movieEntries = entry.getValue();
        LicenseHistoryEntry currentInfo = movieEntries.get(0);
        
        return MovieLicenseDTO.builder()
            .movieId(currentInfo.getMovieId())
            .mainTitle(currentInfo.getMainTitle())
            .type(currentInfo.getType())
            .providerName(currentInfo.getProviderName())
            .currentReleaseDate(currentInfo.getCurrentReleaseDate())
            .currentExpiryDate(currentInfo.getCurrentExpiryDate())
            .currentStatus(calculateStatus(currentInfo.getCurrentReleaseDate(), currentInfo.getCurrentExpiryDate()))
            .licenseHistory(movieEntries)
            .build();
    }

    private String calculateStatus(String releaseDateStr, String expiryDateStr) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime releaseDate = LocalDateTime.parse(releaseDateStr, DATE_FORMATTER);
        LocalDateTime expiryDate = LocalDateTime.parse(expiryDateStr, DATE_FORMATTER);

        if (now.isBefore(releaseDate)) {
            return "PENDING";
        } else if (now.isAfter(expiryDate)) {
            return "EXPIRED";
        } else {
            return "ACTIVE";
        }
    }

    @Transactional
    public void updateLicense(LicenseUpdateRequest request) {
        LicenseHistoryEntry currentEntry = mapper.getLicenseHistoryEntries(1, 1, "change_date DESC")
            .stream()
            .filter(entry -> entry.getMovieId().equals(request.getMovieId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Movie not found"));

        LicenseHistoryEntry newEntry = LicenseHistoryEntry.builder()
            .movieId(request.getMovieId())
            .historicalReleaseDate(currentEntry.getCurrentReleaseDate())
            .historicalExpiryDate(currentEntry.getCurrentExpiryDate())
            .historicalStatus(calculateStatus(currentEntry.getCurrentReleaseDate(), currentEntry.getCurrentExpiryDate()))
            .currentReleaseDate(request.getNewReleaseDate())
            .currentExpiryDate(request.getNewExpiryDate())
            .changeDate(LocalDateTime.now())
            .build();

        mapper.insertLicenseHistory(newEntry);
    }
}
