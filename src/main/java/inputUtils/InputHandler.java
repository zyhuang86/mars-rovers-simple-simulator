package inputUtils;

import com.google.common.collect.LinkedListMultimap;
import enums.CardinalDirection;
import enums.CommandType;
import enums.InputType;
import exceptions.InvalidRoverCommandException;
import exceptions.InvalidInputArgumentException;
import entities.PlateauGridPoint;
import entities.Rover;
import validators.RoverCommandValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static enums.InputType.PLATEAU_UPPER_RIGHT_COORDINATE;
import static enums.InputType.ROVER_INITIAL_POSITION;

public class InputHandler {
   private static final Logger LOG = Logger.getLogger(InputHandler.class.getName());
   private InputData inputData = new InputData();

   private InputHandler(PlateauGridPoint leftCorner,
                        PlateauGridPoint rightCorner,
                        List<Rover> roverList,
                        Map<UUID, List<CommandType>> roverCommandsLinkedHashMap) {
      inputData.setLeftCorner(leftCorner);
      inputData.setRightCorner(rightCorner);
      inputData.setRoverList(roverList);
      inputData.setRoverCommandsLinkedHashMap(roverCommandsLinkedHashMap);
   }

   private InputData getValidatedInputData() {
      return inputData;
   }

   public static class InputValidator {
      private PlateauGridPoint leftCorner;
      private PlateauGridPoint rightCorner;
      private Map<UUID, List<CommandType>> roverCommandsLinkedHashMap = new LinkedHashMap<>();
      private List<PlateauGridPoint> roverStartingPositions = new ArrayList<>();
      private List<List<CommandType>> roverCommandLists = new ArrayList<>();
      private List<CardinalDirection> roverCardinalDirectionList = new ArrayList<>();
      private List<Rover> roverList = new ArrayList<>();

      public InputValidator(LinkedListMultimap<InputType, List<String>> inputTypeListMap)
              throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
         for (Map.Entry<InputType, List<String>> entry : inputTypeListMap.entries())  {
            switch (entry.getKey()) {
               case ROVER_INITIAL_POSITION:
                  Integer roverXCoords = validateStringInteger(entry.getValue().get(0), ROVER_INITIAL_POSITION);
                  Integer roverYCoords = validateStringInteger(entry.getValue().get(1), ROVER_INITIAL_POSITION);
                  CardinalDirection roverCardinalDirection = validateCardinalDirection(entry.getValue().get(2));
                  roverStartingPositions.add(createPlateauPoint(roverXCoords, roverYCoords));
                  roverCardinalDirectionList.add(roverCardinalDirection);
                  break;
               case ROVER_MOVEMENT_COMMAND:
                  roverCommandLists.add(RoverCommandValidator.validateAndGetRoverCommands(entry.getValue().get(0)));
                  break;
               case PLATEAU_UPPER_RIGHT_COORDINATE:
                  Integer upperRightXCoords = validateStringInteger(entry.getValue().get(0), PLATEAU_UPPER_RIGHT_COORDINATE);
                  Integer upperRightYCoords = validateStringInteger(entry.getValue().get(1), PLATEAU_UPPER_RIGHT_COORDINATE);
                  this.rightCorner = createPlateauPoint(upperRightXCoords, upperRightYCoords);
                  break;
               default:
                  break;
            }
         }
      }

      public InputValidator loadConfiguration(Integer leftX, Integer leftY) {
         this.leftCorner = createPlateauPoint(leftX, leftY);
         return this;
      }

      public InputData validateAndGetInputEntries() throws InvalidRoverCommandException {
         for (int index = 0; index < roverStartingPositions.size(); index++) {
            createRoverAndAddCommands(roverStartingPositions.get(index),
                    roverCardinalDirectionList.get(index),
                    roverCommandLists.get(index));
         }
         InputHandler inputHandler = new InputHandler(leftCorner, rightCorner, roverList, roverCommandsLinkedHashMap);
         return inputHandler.getValidatedInputData();
      }

      private void createRoverAndAddCommands(PlateauGridPoint roverStartingPosition,
                                             CardinalDirection cardinalDirection,
                                             List<CommandType> commandTypes)
              throws InvalidRoverCommandException {
         Rover rover = new Rover(roverStartingPosition, cardinalDirection);
         roverList.add(rover);
         roverCommandsLinkedHashMap.putIfAbsent(rover.getId(), commandTypes);
      }

      private PlateauGridPoint createPlateauPoint(Integer xCoords, Integer yCoords) {
         PlateauGridPoint plateauGridPoint = new PlateauGridPoint();
         plateauGridPoint.setX(xCoords);
         plateauGridPoint.setY(yCoords);
         return plateauGridPoint;
      }

      private Integer validateStringInteger(String integerString, InputType inputType)
              throws InvalidInputArgumentException {
         Integer integer;
         try {
            integer = Integer.parseInt(integerString);
         } catch (NumberFormatException exp) {
            throw new InvalidInputArgumentException(integerString + " is not a valid input for " + inputType);
         }
         return integer;
      }

      private CardinalDirection validateCardinalDirection(String cardinalString) throws InvalidInputArgumentException {
         CardinalDirection cardinalDirection;
         try {
            cardinalDirection = CardinalDirection.fromString(cardinalString);
         } catch (IllegalArgumentException exp) {
            throw new InvalidInputArgumentException(cardinalString + " is not a valid cardinal string");
         }
         return cardinalDirection;
      }
   }
}

