package nl.emil.axontest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class BikeStatus {

  @Id private String bikeId;
  private String bikeType;
  private String location;
  private String renter;
  private RentalStatus status;

  public BikeStatus() {}

  public BikeStatus(String bikeId, String bikeType, String location) {
    this.bikeId = bikeId;
    this.bikeType = bikeType;
    this.location = location;
  }

  public String description() {
    return switch (status) {
      case RENTED -> String.format("Bike %s was rented by %s in %s", bikeId, renter, location);
      case AVAILABLE -> String.format("Bike %s is available for rental in %s.", bikeId, location);
      case REQUESTED -> String.format("Bike %s is requested by %s in %s", bikeId, renter, location);
      default -> "Status unknown";
    };
  }
}
