package validators;

import exceptions.OutOfPlateauBoundsException;
import entities.PlateauGrid;
import entities.Rover;

public class RoverPlacementValidator {
   public static Boolean validateRoverPlacementOnGrid(PlateauGrid plateauGrid, Rover rover)
           throws OutOfPlateauBoundsException {
      if (rover.getCurrentPosition().getX() < plateauGrid.getLeftCorner().getX() ||
              rover.getCurrentPosition().getY() < plateauGrid.getLeftCorner().getY() ||
              rover.getCurrentPosition().getX() > plateauGrid.getRightCorner().getX() ||
              rover.getCurrentPosition().getY() > plateauGrid.getRightCorner().getY()) {
         throw new OutOfPlateauBoundsException(rover);
      }
      return true;
   }
}
