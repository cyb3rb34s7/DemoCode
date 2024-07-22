@Mapper
public interface MovieLicenseMapper {
    List<MovieLicenseDTO> getMovieLicenses(
        @Param("sortCol") String sortCol,
        @Param("sortOrder") String sortOrder,
        @Param("limit") int limit,
        @Param("offset") int offset
    );

    void insertLicenseHistory(LicenseHistoryEntry entry);
}
