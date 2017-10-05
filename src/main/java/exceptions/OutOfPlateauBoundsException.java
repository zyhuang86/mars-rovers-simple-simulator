package exceptions;

import entities.Rover;

public class OutOfPlateauBoundsException extends Exception {
   public OutOfPlateauBoundsException(Rover rover) {
      super(String.format("Rover position is not within the range of the plateau grid. (%d, %d)",
              rover.getCurrentPosition().getX(), rover.getCurrentPosition().getY()));
   }
}
