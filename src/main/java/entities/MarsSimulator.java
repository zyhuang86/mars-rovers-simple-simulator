package entities;

import enums.CommandType;
import exceptions.InvalidPlateauGridException;
import exceptions.OutOfPlateauBoundsException;
import validators.RoverPlacementValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class MarsSimulator {
   private static final Logger LOG = Logger.getLogger(MarsSimulator.class.getName());
   private PlateauGrid plateauGrid;
   private Map<UUID, Rover> roverMap;

   private MarsSimulator(PlateauGrid plateauGrid, Map<UUID, Rover> roverMap) {
      this.plateauGrid = plateauGrid;
      this.roverMap = roverMap;
   }

   public static class SimulatorBuilder {
      private PlateauGrid plateauGrid;
      private Map<UUID, Rover> roverToIdMap = new LinkedHashMap<>();

      public SimulatorBuilder createPlateauGrid(PlateauGridPoint leftCorner, PlateauGridPoint rightCorner)
              throws InvalidPlateauGridException {
         if (leftCorner == null || rightCorner == null) {
            throw new InvalidPlateauGridException(
                    "Left and right corner coordinates are not correctly defined for plateau.");
         }
         plateauGrid = new PlateauGrid(leftCorner, rightCorner);
         return this;
      }

      public SimulatorBuilder addRovers(List<Rover> roverList) throws OutOfPlateauBoundsException {
         for (Rover rover : roverList) {
            if (RoverPlacementValidator.validateRoverPlacementOnGrid(plateauGrid, rover)) {
               roverToIdMap.put(rover.getId(), rover);
            }
         }
         return this;
      }

      public MarsSimulator simulate() throws OutOfPlateauBoundsException {
         return new MarsSimulator(plateauGrid, roverToIdMap);
      }
   }

   public void sendCommandToRover(UUID roverID, List<CommandType> commandList) throws OutOfPlateauBoundsException {
      for(CommandType commandType : commandList) {
         Rover rover = roverMap.get(roverID);
         if (rover != null) {
            LOG.info(String.format("Rover %s: current position -  %d %d %s ", roverID,
                    rover.getCurrentPosition().getX(),
                    rover.getCurrentPosition().getY(),
                    rover.getCardinalDirection()));
            rover.command(commandType);
            RoverPlacementValidator.validateRoverPlacementOnGrid(plateauGrid, rover);
            LOG.info(String.format("Rover %s: receivedCommand - %s", roverID, commandType));
            LOG.info(String.format("Rover %s: new position %d %d %s ", roverID,
                    rover.getCurrentPosition().getX(),
                    rover.getCurrentPosition().getY(),
                    rover.getCardinalDirection()));
         }
      }
   }

   public Map<UUID, Rover> getCurrentRoverPositions() {
      return roverMap;
   }
}
