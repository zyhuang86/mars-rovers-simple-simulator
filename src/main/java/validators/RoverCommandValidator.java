package validators;

import enums.CommandType;
import exceptions.InvalidInputArgumentException;
import exceptions.InvalidRoverCommandException;

import java.util.ArrayList;
import java.util.List;

public class RoverCommandValidator {
   private static final String VALID_CARDINAL_DIRECTION_STRING_PATTERN = "[LRM]+";

   public static List<CommandType> validateAndGetRoverCommands(String commandLetterList)
           throws InvalidRoverCommandException, InvalidInputArgumentException {
      if (!commandLetterList.matches(VALID_CARDINAL_DIRECTION_STRING_PATTERN)) {
         throw new InvalidInputArgumentException(commandLetterList + " contains invalid movement type. Only L R M are allowed.");
      }

      List<CommandType> commandTypeList = new ArrayList<>();
      for (int index = 0; index < commandLetterList.length(); index++) {
         char commandLetter = commandLetterList.charAt(index);
         CommandType commandType;
         try {
            commandType = CommandType.fromString(commandLetterList.charAt(index));
         } catch (IllegalArgumentException exp) {
            throw new InvalidRoverCommandException(commandLetter);
         }
         commandTypeList.add(commandType);
      }
      return commandTypeList;
   }
}
