package inputUtils;

import enums.InputType;
import exceptions.InvalidInputArgumentException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InputFileParserTest {
   private static final String INCORRECT_UPPER_RIGHT_COORDS_ARGUMENT_COUNT = "incorrectUpperRightCoordinateArgCount.txt";
   private static final String INCORRECT_ROVER_INIT_POSITIONS_ARGUMENT_COUNT = "incorrectRoverPositionArgCount.txt";
   private static final String MISSING_ROVER_COMMANDS = "missingRoverCommands.txt";
   private static final String NON_EXISTENT_FILE = "nonExistentFile.txt";
   private static final String VALID_INPUT = "validInput.txt";

   @Test(expected = InvalidInputArgumentException.class)
   public void testFileRead_NoFileProvided_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException {
      new InputFileParser(new String[0]);
   }

   @Test(expected = FileNotFoundException.class)
   public void testFileRead_InvalidFileProvided_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException {
      String[] args = {NON_EXISTENT_FILE};
      new InputFileParser(args);
   }

   @Test(expected = InvalidInputArgumentException.class)
   public void testFileRead_IncorrectUpperRightCoordsArgCount_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException {
      loadFileAndCreateParser(INCORRECT_UPPER_RIGHT_COORDS_ARGUMENT_COUNT);
   }

   @Test(expected = InvalidInputArgumentException.class)
   public void testFileRead_IncorrectRoverInitialPositionArgCount_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException {
      loadFileAndCreateParser(INCORRECT_ROVER_INIT_POSITIONS_ARGUMENT_COUNT);
   }

   @Test(expected = InvalidInputArgumentException.class)
   public void testFileRead_MissingRoverCommands_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException {
      loadFileAndCreateParser(MISSING_ROVER_COMMANDS);
   }

   @Test
   public void testFileRead_ValidInputFile_ExpectSuccessfulParse()
           throws IOException, InvalidInputArgumentException {
      URL filePath = getClass().getClassLoader().getResource(VALID_INPUT);
      String[] args = {filePath != null ? filePath.getPath() : null};
      InputFileParser inputFileParser = new InputFileParser(args);
      for (Map.Entry<InputType, List<String>> entry : inputFileParser.getCommands().entries())  {
         String expectedString;
         String receivedString;
         switch (entry.getKey()) {
            case ROVER_INITIAL_POSITION:
               expectedString = "1 2 N";
               receivedString = entry.getValue().get(0) + " " + entry.getValue().get(1)
                       + " " + entry.getValue().get(2);
               assertEquals(expectedString, receivedString);
               break;
            case ROVER_MOVEMENT_COMMAND:
               expectedString = "LMLMLMLMM";
               assertEquals(expectedString, entry.getValue().get(0));
               break;
            case PLATEAU_UPPER_RIGHT_COORDINATE:
               expectedString = "5 5";
               receivedString = entry.getValue().get(0) + " " + entry.getValue().get(1);
               assertEquals(expectedString, receivedString);
               break;
            default:
               break;
         }
      }
   }

   private void loadFileAndCreateParser(String resourceName)
           throws IOException, InvalidInputArgumentException {
      URL filePath = getClass().getClassLoader().getResource(resourceName);
      String[] args = {filePath != null ? filePath.getPath() : null};
      new InputFileParser(args);
   }
}
