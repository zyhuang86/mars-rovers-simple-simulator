package exceptions;

public class InvalidRoverCommandException extends Exception {
   public InvalidRoverCommandException(char invalidCommand) {
      super("Invalid command found: " + invalidCommand);
   }
}
