package nl.emil.axontest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentalController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  /*private final BikeRentalDataGenerator bikeRentalDataGenerator;*/

  public RentalController(CommandGateway commandGateway, QueryGateway queryGateway/*, BikeRentalDataGenerator bikeRentalDataGenerator*/) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
    /*this.bikeRentalDataGenerator = bikeRentalDataGenerator;*/
  }


  @PostMapping("/bikes")
  public CompletableFuture<String> registerBike(
      @RequestParam("bikeType") String bikeType,
      @RequestParam("location") String location) {

    RegisterBikeCommand registerBikeCommand =
        new RegisterBikeCommand(
            UUID.randomUUID().toString(),
            bikeType,
            location);

    CompletableFuture<String> commandResult =
        commandGateway.send(registerBikeCommand);

    return commandResult;
  }

  @GetMapping("/bikes")
  public CompletableFuture<List<BikeStatus>> findAll() {
    return queryGateway.query(
        BikeStatusNamedQueries.FIND_ALL,
        null,
        ResponseTypes.multipleInstancesOf(BikeStatus.class)
    );
  }

  @GetMapping("/bikes/{bikeId}")
  public CompletableFuture<BikeStatus> findStatus(@PathVariable("bikeId") String bikeId) {
    return queryGateway.query(BikeStatusNamedQueries.FIND_ONE, bikeId, BikeStatus.class);
  }
}