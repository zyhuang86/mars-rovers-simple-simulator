package enums;

public enum InputType {
   ROVER_MOVEMENT_COMMAND(1),
   PLATEAU_UPPER_RIGHT_COORDINATE(2),
   ROVER_INITIAL_POSITION(3);

   private int expectedParameterCount;

   InputType(int expectedParameterCount) {
      this.expectedParameterCount = expectedParameterCount;
   }

   public int getExpectedParameterCount() {
      return expectedParameterCount;
   }
}
