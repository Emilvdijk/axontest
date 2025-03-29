package nl.emil.axontest;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Bike {

  @AggregateIdentifier
  private String bikeId;

  private boolean isAvailable;
  private String reservedBy;
  private boolean reservationConfirmed;

  public Bike() {
  }

  @CommandHandler
  public Bike(RegisterBikeCommand command) {
    apply(new BikeRegisteredEvent(command.bikeId(), command.bikeType(), command.location()));
  }

  @EventSourcingHandler
  protected void handle(BikeRegisteredEvent event) {
    this.bikeId = event.bikeId();
    this.isAvailable = true;
  }


}