package nl.emil.axontest;

import java.util.ArrayList;
import java.util.List;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class BikeStatusProjection {

  private final BikeStatusRepository bikeStatusRepository;

  public BikeStatusProjection(BikeStatusRepository repository) {
    this.bikeStatusRepository = repository;
  }

  @EventHandler
  public void on(BikeRegisteredEvent event) {
    var bikeStatus = new BikeStatus(event.bikeId(), event.bikeType(), event.location());
    bikeStatusRepository.save(bikeStatus);
  }
  @QueryHandler(queryName = BikeStatusNamedQueries.FIND_ALL)
  public Iterable<BikeStatus> findAll() {
    return bikeStatusRepository.findAll();
  }

  @QueryHandler(queryName = BikeStatusNamedQueries.FIND_AVAILABLE)
  public List<BikeStatus> findAvailable(String bikeType) {
    return bikeStatusRepository.findAllByBikeTypeAndStatus(bikeType, RentalStatus.AVAILABLE);
  }

  @QueryHandler(queryName = BikeStatusNamedQueries.FIND_ONE)
  public BikeStatus findOne(String bikeId) {
    return bikeStatusRepository.findById(bikeId).orElse(null);
  }

}