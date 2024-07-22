@RestController
@RequestMapping("/api/movie-licenses")
public class MovieLicenseController {
    private final MovieLicenseService service;

    public MovieLicenseController(MovieLicenseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovieLicenseDTO>> getMovieLicenses(
            @RequestParam(defaultValue = "movie_id") String sortCol,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        try {
            List<MovieLicenseDTO> result = service.getMovieLicenses(sortCol, sortOrder, limit, offset);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/log-history")
    public ResponseEntity<Void> logLicenseHistory(@RequestBody LicenseHistoryRequest request) {
        try {
            service.logLicenseHistory(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
