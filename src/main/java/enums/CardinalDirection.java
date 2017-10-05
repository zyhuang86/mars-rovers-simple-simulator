package enums;

public enum CardinalDirection {
   NORTH("N"),
   SOUTH("S"),
   EAST("E"),
   WEST("W");

   private String cardinalString;

   CardinalDirection(final String cardinalString) {
      this.cardinalString = cardinalString;
   }

   @Override
   public String toString() {
      return cardinalString;
   }

   public static CardinalDirection fromString(String cardinalString) {
      switch (cardinalString) {
         case "N":
            return NORTH;
         case "S":
            return SOUTH;
         case "E":
            return EAST;
         case "W":
            return WEST;
         default:
            throw new IllegalArgumentException();
      }
   }
}
