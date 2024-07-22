@Service
public class MovieLicenseService {
    private final MovieLicenseRepository repository;

    @Autowired
    public MovieLicenseService(MovieLicenseRepository repository) {
        this.repository = repository;
    }

    public List<MovieLicenseDTO> getMovieLicensesWithHistory(int page, int size, String sort) {
        List<LicenseHistoryEntry> entries = repository.getLicenseHistoryEntries(page, size, sort);
        
        // Group entries by movieId
        Map<Long, List<LicenseHistoryEntry>> groupedEntries = entries.stream()
            .collect(Collectors.groupingBy(LicenseHistoryEntry::getMovieId));
        
        // Convert grouped entries to MovieLicenseDTO list
        List<MovieLicenseDTO> result = new ArrayList<>();
        for (Map.Entry<Long, List<LicenseHistoryEntry>> entry : groupedEntries.entrySet()) {
            List<LicenseHistoryEntry> movieEntries = entry.getValue();
            
            // All entries for a movie will have the same current information
            LicenseHistoryEntry currentInfo = movieEntries.get(0);
            
            MovieLicenseDTO dto = new MovieLicenseDTO();
            dto.setMovieId(currentInfo.getMovieId());
            dto.setMainTitle(currentInfo.getMainTitle());
            dto.setProviderName(currentInfo.getProviderName());
            dto.setReleaseDate(currentInfo.getCurrentReleaseDate());
            dto.setExpiryDate(currentInfo.getCurrentExpiryDate());
            dto.setCurrentStatus(calculateStatus(currentInfo.getCurrentReleaseDate(), currentInfo.getCurrentExpiryDate()));
            dto.setLicenseHistory(movieEntries);
            
            result.add(dto);
        }
        
        return result;
    }

    private String calculateStatus(LocalDate releaseDate, LocalDate expiryDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(releaseDate)) {
            return "PENDING";
        } else if (now.isAfter(expiryDate)) {
            return "EXPIRED";
        } else {
            return "ACTIVE";
        }
    }
}
