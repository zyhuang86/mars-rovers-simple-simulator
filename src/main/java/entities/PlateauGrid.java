package entities;

import exceptions.InvalidPlateauGridException;

public class PlateauGrid {
   private PlateauGridPoint leftCorner;
   private PlateauGridPoint rightCorner;

   public PlateauGrid(PlateauGridPoint leftCorner, PlateauGridPoint rightCorner) throws InvalidPlateauGridException {
      if (rightCorner.getY() <= leftCorner.getY() || rightCorner.getX() <= leftCorner.getX()) {
         throw new InvalidPlateauGridException(
                 String.format("Invalid upper right corner coordinates for plateau. (%d, %d)",
                         rightCorner.getX(), rightCorner.getY()));
      }
      this.leftCorner = leftCorner;
      this.rightCorner = rightCorner;
   }

   public PlateauGridPoint getRightCorner() {
      return rightCorner;
   }

   public PlateauGridPoint getLeftCorner() {
      return leftCorner;
   }
}
