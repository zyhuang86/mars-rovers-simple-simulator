package enums;

public enum CommandType {
   ROTATE_LEFT("L"),
   ROTATE_RIGHT("R"),
   MOVE("M");

   private final String stringCommand;

   CommandType(final String stringCommand) {
      this.stringCommand = stringCommand;
   }

   @Override
   public String toString() {
      return stringCommand;
   }

   public static CommandType fromString(char commandLetter) {
      switch (commandLetter) {
         case 'L':
            return ROTATE_LEFT;
         case 'R':
            return ROTATE_RIGHT;
         case 'M':
            return MOVE;
         default:
            throw new IllegalArgumentException();
      }
   }
}
