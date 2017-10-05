package entities;

import enums.CardinalDirection;
import enums.CommandType;

import java.util.UUID;

public class Rover {
   private UUID id;
   private PlateauGridPoint currentPosition;
   private CardinalDirection cardinalDirection;

   public Rover(PlateauGridPoint startingPosition, CardinalDirection cardinalDirection) {
      this.setId(UUID.randomUUID());
      this.currentPosition = startingPosition;
      this.cardinalDirection = cardinalDirection;
   }

   public PlateauGridPoint getCurrentPosition() {
      return currentPosition;
   }

   public CardinalDirection getCardinalDirection() {
      return cardinalDirection;
   }

   public void command(CommandType commandType) {
      switch (commandType) {
         case ROTATE_LEFT:
            rotateLeft();
            break;
         case ROTATE_RIGHT:
            rotateRight();
            break;
         case MOVE:
            move();
            break;
         default:
            break;
      }
   }

   private void move() {
      PlateauGridPoint newPosition = new PlateauGridPoint();
      switch (cardinalDirection) {
         case SOUTH:
            newPosition.setX(currentPosition.getX());
            newPosition.setY(currentPosition.getY() - 1);
            break;
         case NORTH:
            newPosition.setX(currentPosition.getX());
            newPosition.setY(currentPosition.getY() + 1);
            break;
         case EAST:
            newPosition.setX(currentPosition.getX() + 1);
            newPosition.setY(currentPosition.getY());
            break;
         case WEST:
            newPosition.setX(currentPosition.getX() - 1);
            newPosition.setY(currentPosition.getY());
            break;
         default:
            newPosition = currentPosition;
            break;
      }
      currentPosition = newPosition;
   }

   private void rotateLeft() {
      CardinalDirection newCardinalDirection = cardinalDirection;
      switch (cardinalDirection) {
         case EAST:
            newCardinalDirection = CardinalDirection.NORTH;
            break;
         case WEST:
            newCardinalDirection = CardinalDirection.SOUTH;
            break;
         case NORTH:
            newCardinalDirection = CardinalDirection.WEST;
            break;
         case SOUTH:
            newCardinalDirection = CardinalDirection.EAST;
            break;
      }
      this.cardinalDirection = newCardinalDirection;
   }

   private void rotateRight() {
      CardinalDirection newCardinalDirection = cardinalDirection;
      switch (cardinalDirection) {
         case EAST:
            newCardinalDirection = CardinalDirection.SOUTH;
            break;
         case WEST:
            newCardinalDirection = CardinalDirection.NORTH;
            break;
         case NORTH:
            newCardinalDirection = CardinalDirection.EAST;
            break;
         case SOUTH:
            newCardinalDirection = CardinalDirection.WEST;
            break;
      }
      this.cardinalDirection = newCardinalDirection;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }
}
