package nl.emil.axontest;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeStatusRepository
    extends JpaRepository<BikeStatus, String> {

  List<BikeStatus> findAllByBikeTypeAndStatus(String bikeType, RentalStatus rentalStatus);
  long countBikeStatusesByBikeType(String bikeType);
}
