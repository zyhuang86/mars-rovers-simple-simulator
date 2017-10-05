package entities;

import enums.CardinalDirection;
import enums.CommandType;
import exceptions.InvalidInputArgumentException;
import exceptions.InvalidPlateauGridException;
import exceptions.InvalidRoverCommandException;
import exceptions.OutOfPlateauBoundsException;
import org.junit.Before;
import org.junit.Test;
import validators.RoverCommandValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class MarsSimulatorTest {
   private PlateauGridPoint validLeftCorner = new PlateauGridPoint(0, 0);
   private PlateauGridPoint validRightCorner = new PlateauGridPoint(5, 5);
   private PlateauGridPoint invalidRightCorner = new PlateauGridPoint(0,0);
   private PlateauGridPoint pointWithinPlateauBounds = new PlateauGridPoint(1, 2);
   private PlateauGridPoint pointOutsidePlateauBounds = new PlateauGridPoint(-1, -1);
   private List<CommandType> roverCommandsWithinPlateauBounds;
   private List<CommandType> roverCommandsOutsidePlateauBounds;
   private final static String VALID_COMMAND_STRING = "LMLMLMLMM";
   private final static String INVALID_COMMAND_STRING = "MMMMMMMMMMM";

   @Before
   public void init() throws InvalidRoverCommandException, InvalidInputArgumentException {
      roverCommandsWithinPlateauBounds = RoverCommandValidator.validateAndGetRoverCommands(VALID_COMMAND_STRING);
      roverCommandsOutsidePlateauBounds = RoverCommandValidator.validateAndGetRoverCommands(INVALID_COMMAND_STRING);
   }

   @Test(expected = InvalidPlateauGridException.class)
   public void testMarsSimulator_invalidPlateauGridInput_ExpectInvalidPlateauGridException()
           throws InvalidPlateauGridException, OutOfPlateauBoundsException {
      new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(validLeftCorner, invalidRightCorner)
              .addRovers(new ArrayList<>())
              .simulate();
   }

   @Test(expected = OutOfPlateauBoundsException.class)
   public void testMarsSimulator_invalidRoverPlacement_ExpectOutOfPlateauBoundsException()
           throws InvalidPlateauGridException, OutOfPlateauBoundsException {
      new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(validLeftCorner, validRightCorner)
              .addRovers(Arrays.asList(new Rover(pointOutsidePlateauBounds, CardinalDirection.WEST)))
              .simulate();
   }

   @Test
   public void testMarsSimulator_validRoverPlacement_ExpectGridCreationAndRoverSuccessfulPlacement()
           throws InvalidPlateauGridException, OutOfPlateauBoundsException {
      new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(validLeftCorner, validRightCorner)
              .addRovers(Arrays.asList(new Rover(pointWithinPlateauBounds, CardinalDirection.WEST)))
              .simulate();
   }

   @Test
   public void testMarsSimulator_validRoverMovement_ExpectGridCreationAndRoverSuccessfulPlacement()
           throws InvalidPlateauGridException, OutOfPlateauBoundsException, InvalidRoverCommandException,
           InvalidInputArgumentException {
      Rover rover = new Rover(pointWithinPlateauBounds, CardinalDirection.NORTH);
      MarsSimulator marsSimulator = new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(validLeftCorner, validRightCorner)
              .addRovers(Arrays.asList(rover))
              .simulate();

      marsSimulator.sendCommandToRover(rover.getId(), roverCommandsWithinPlateauBounds);

      Rover expectedRoverPosition = new Rover(new PlateauGridPoint(1,3), CardinalDirection.NORTH);
      expectedRoverPosition.setId(rover.getId());
      assertReflectionEquals(expectedRoverPosition,
              marsSimulator.getCurrentRoverPositions().entrySet().iterator().next().getValue());
   }

   @Test(expected = OutOfPlateauBoundsException.class)
   public void testMarsSimulator_invalidRoverMovement_ExpectOutOfPlateauBoundsException()
           throws InvalidPlateauGridException, OutOfPlateauBoundsException, InvalidRoverCommandException,
           InvalidInputArgumentException {
      Rover rover = new Rover(pointWithinPlateauBounds, CardinalDirection.NORTH);
      MarsSimulator marsSimulator = new MarsSimulator.SimulatorBuilder()
              .createPlateauGrid(validLeftCorner, validRightCorner)
              .addRovers(Arrays.asList(rover))
              .simulate();

      marsSimulator.sendCommandToRover(rover.getId(), roverCommandsOutsidePlateauBounds);
   }
}
