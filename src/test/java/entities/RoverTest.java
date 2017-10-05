package entities;

import enums.CardinalDirection;
import enums.CommandType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class RoverTest {
   private final PlateauGridPoint startingPlateauPoint = new PlateauGridPoint(1,1);

   @Test
   public void testRoverMovement_sendAllRotateRightCommands_ExpectCorrectRotation() {
      Rover rover = new Rover(startingPlateauPoint, CardinalDirection.NORTH);
      rover.command(CommandType.ROTATE_RIGHT);
      assertEquals(CardinalDirection.EAST, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_RIGHT);
      assertEquals(CardinalDirection.SOUTH, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_RIGHT);
      assertEquals(CardinalDirection.WEST, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_RIGHT);
      assertEquals(CardinalDirection.NORTH, rover.getCardinalDirection());
   }

   @Test
   public void testRoverMovement_sendAllRotateLeftCommands_ExpectCorrectRotation() {
      Rover rover = new Rover(startingPlateauPoint, CardinalDirection.NORTH);
      rover.command(CommandType.ROTATE_LEFT);
      assertEquals(CardinalDirection.WEST, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_LEFT);
      assertEquals(CardinalDirection.SOUTH, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_LEFT);
      assertEquals(CardinalDirection.EAST, rover.getCardinalDirection());
      rover.command(CommandType.ROTATE_LEFT);
      assertEquals(CardinalDirection.NORTH, rover.getCardinalDirection());
   }

   @Test
   public void testRoverMovement_sendMoveCommandsForAllDirections_ExpectCorrectMovement() {
      moveRoverAndVerifyPosition(CardinalDirection.NORTH, new PlateauGridPoint(1, 2));
      moveRoverAndVerifyPosition(CardinalDirection.SOUTH, new PlateauGridPoint(1, 0));
      moveRoverAndVerifyPosition(CardinalDirection.EAST, new PlateauGridPoint(2, 1));
      moveRoverAndVerifyPosition(CardinalDirection.WEST, new PlateauGridPoint(0, 1));
   }

   private void moveRoverAndVerifyPosition(CardinalDirection startingDirection,
                                           PlateauGridPoint expectedRoverPosition ) {
      Rover rover = new Rover(startingPlateauPoint, startingDirection);
      rover.command(CommandType.MOVE);
      assertReflectionEquals(expectedRoverPosition, rover.getCurrentPosition());
   }
}
