package inputUtils;

import entities.PlateauGridPoint;
import enums.CommandType;
import exceptions.InvalidInputArgumentException;
import exceptions.InvalidRoverCommandException;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


public class InputHandlerTest {
   private static final String INVALID_UPPER_RIGHT_COORDINATES = "invalidUpperRightCoordinates.txt";
   private static final String INVALID_INITIAL_ROVER_POSITION = "invalidInitialRoverPosition.txt";
   private static final String INVALID_ROVER_COMMAND = "invalidRoverCommands.txt";
   private static final String VALID_INPUT = "validInput.txt";
   private static final Integer LEFT_CORNER_X = 0;
   private static final Integer LEFT_CORNER_Y = 0;

   @Test(expected = InvalidInputArgumentException.class)
   public void testInputValidate_InvalidUpperRightCoordinates_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
      loadFileAndInvokeInputDataValidation(INVALID_UPPER_RIGHT_COORDINATES);
   }

   @Test(expected = InvalidInputArgumentException.class)
   public void testInputValidate_InvalidRoverInitialPosition_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
      loadFileAndInvokeInputDataValidation(INVALID_INITIAL_ROVER_POSITION);
   }

   @Test(expected = InvalidInputArgumentException.class)
   public void testInputValidate_InvalidRoverCommands_ExpectInvalidInputArgumentException()
           throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
      loadFileAndInvokeInputDataValidation(INVALID_ROVER_COMMAND);
   }

   @Test
   public void testInputValidate_ValidInputs_ExpectSuccessValidation()
           throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
      InputData inputData = loadFileAndInvokeInputDataValidation(VALID_INPUT);
      PlateauGridPoint expectedLeftCornerCoords = new PlateauGridPoint();
      expectedLeftCornerCoords.setX(LEFT_CORNER_X);
      expectedLeftCornerCoords.setY(LEFT_CORNER_Y);
      assertReflectionEquals(expectedLeftCornerCoords, inputData.getLeftCorner());

      PlateauGridPoint expectedRightCornerCoords = new PlateauGridPoint();
      expectedRightCornerCoords.setX(5);
      expectedRightCornerCoords.setY(5);
      assertReflectionEquals(expectedRightCornerCoords, inputData.getRightCorner());

      String expectedCommandString = "LMLMLMLMM";
      String receivedCommandString = "";
      for (Map.Entry<UUID, List<CommandType>> entry : inputData.getRoverCommandsLinkedHashMap().entrySet()) {
         for (CommandType commandType : entry.getValue()) {
            receivedCommandString += commandType;
         }
      }
      assertEquals(expectedCommandString, receivedCommandString);
   }

   private InputData loadFileAndInvokeInputDataValidation(String resourceName)
           throws IOException, InvalidInputArgumentException, InvalidRoverCommandException {
      URL filePath = getClass().getClassLoader().getResource(resourceName);
      String[] args = {filePath != null ? filePath.getPath() : null};
      return new InputHandler.InputValidator((new InputFileParser(args)).getCommands())
              .loadConfiguration(LEFT_CORNER_X, LEFT_CORNER_Y)
              .validateAndGetInputEntries();
   }
}
