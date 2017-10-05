import entities.Rover;
import enums.CommandType;
import exceptions.InvalidRoverCommandException;
import exceptions.InvalidInputArgumentException;
import exceptions.InvalidPlateauGridException;
import exceptions.OutOfPlateauBoundsException;
import inputUtils.InputData;
import entities.MarsSimulator;
import inputUtils.InputFileParser;
import inputUtils.InputHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class MarsRoverApplication {
   private static final Logger LOG = Logger.getLogger(MarsRoverApplication.class.getName());
   public static void main(String[] args) throws IOException, InvalidInputArgumentException,
           InvalidRoverCommandException, InvalidPlateauGridException, OutOfPlateauBoundsException {
      LOG.info("Load configuration and validate input data");
      MarsRoverConfiguration configuration = (new MarsRoverConfigurationLoader()).getConfiguration();
      InputData inputData = new InputHandler.InputValidator((new InputFileParser(args)).getCommands())
              .loadConfiguration(configuration.getLeftCornerX(), configuration.getLeftCornerY())
              .validateAndGetInputEntries();

      // create a Mars simulator, add the plateau grid, and place all the rovers
      LOG.info("Simulator started");
      MarsSimulator marsSimulator = new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(inputData.getLeftCorner(), inputData.getRightCorner())
              .addRovers(inputData.getRoverList())
              .simulate();

      // send commands to the rovers
      for (Map.Entry<UUID, List<CommandType>> entry : inputData.getRoverCommandsLinkedHashMap().entrySet()) {
         marsSimulator.sendCommandToRover(entry.getKey(), entry.getValue());
      }

      outputRoverPositions(marsSimulator.getCurrentRoverPositions());
   }

   private static void outputRoverPositions(Map<UUID, Rover> roverMap) {
      for (Map.Entry<UUID, Rover> entry : roverMap.entrySet()) {
         Rover rover = entry.getValue();
         System.out.println(rover.getCurrentPosition().getX() + " " +
                 rover.getCurrentPosition().getY() + " " +
                 rover.getCardinalDirection());
      }
   }
}
