package inputUtils;

import com.google.common.collect.LinkedListMultimap;
import enums.InputType;
import exceptions.InvalidInputArgumentException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class InputFileParser {
   private static final Logger LOG = Logger.getLogger(InputFileParser.class.getName());
   private LinkedListMultimap<InputType, List<String>> commandStringMap = LinkedListMultimap.create();

   public InputFileParser(String[] argv) throws InvalidInputArgumentException, IOException {
      BufferedReader reader = getFileReader(getInputFileName(argv));
      parseFileByLineAndValidateArgumentCount(reader);
      reader.close();
   }

   public LinkedListMultimap<InputType, List<String>> getCommands() {
      return commandStringMap;
   }

   private void parseFileByLineAndValidateArgumentCount(BufferedReader reader)
           throws IOException, InvalidInputArgumentException {
      String stringLine;
      Integer lineIndex = 0;

      while ((stringLine = reader.readLine()) != null) {
         StringTokenizer tokens = new StringTokenizer(stringLine);

         // first line should determine the size of plateau grid
         if (lineIndex == 0) {
            if (validateArgumentCount(tokens.countTokens(), InputType.PLATEAU_UPPER_RIGHT_COORDINATE, stringLine)) {
               LOG.info(String.format("Input data received: %s %s:", InputType.PLATEAU_UPPER_RIGHT_COORDINATE, stringLine));
               commandStringMap.put(InputType.PLATEAU_UPPER_RIGHT_COORDINATE, convertStringTokenizerToList(tokens));
            }
         } else {
            // odd line number determines the initial rover deployment location
            if (lineIndex % 2 == 1) {
               if (validateArgumentCount(tokens.countTokens(), InputType.ROVER_INITIAL_POSITION, stringLine)) {
                  LOG.info(String.format("Input data received: %s %s:", InputType.ROVER_INITIAL_POSITION, stringLine));
                  commandStringMap.put(InputType.ROVER_INITIAL_POSITION, convertStringTokenizerToList(tokens));
               }
            }
            // even line number should be a continuous string of movement commands
            else if (lineIndex % 2 == 0) {
               if (validateArgumentCount(tokens.countTokens(), InputType.ROVER_MOVEMENT_COMMAND, stringLine)) {
                  LOG.info(String.format("Input data received: %s %s:", InputType.ROVER_MOVEMENT_COMMAND, stringLine));
                  commandStringMap.put(InputType.ROVER_MOVEMENT_COMMAND, convertStringTokenizerToList(tokens));
               }
            }
         }
         lineIndex++;
      }

      if (lineIndex % 2 == 0) {
         throw new InvalidInputArgumentException("Invalid number of input lines. Correct format is: \n\n" +
                 "\tPlateauGrid grid size: int int \n" +
                 "\tRover #1 position: int int char(N,S,W,E) \n" +
                 "\tRover #1 movement commands: string(L,R,M)\n" +
                 "\t...\n" +
                 "\t...\n" +
                 "\tRover #N position: int int char(N,S,W,E) \n" +
                 "\tRover #N movement commands: string(L,R,M)\n\n" +
                 "\tExample:\n" +
                 "\t 5 5\n" +
                 "\t 1 2 N\n" +
                 "\t LMLMLMLMM\n" +
                 "\t 1 2 N\n" +
                 "\t LMLMLMLMM\n");
      }
   }

   private Boolean validateArgumentCount(Integer receivedParameterCount,
                                         InputType inputType,
                                         String inputString) throws InvalidInputArgumentException {
      if (receivedParameterCount != inputType.getExpectedParameterCount()) {
         String message = String.format("%s is not a valid input for %s. Expected number of arguments: %d",
                 inputString, inputType.toString(), inputType.getExpectedParameterCount());
         LOG.warning(message);
         throw new InvalidInputArgumentException(message);
      }
      return true;
   }

   private List<String> convertStringTokenizerToList(StringTokenizer tokens) {
      List<String> stringList = new ArrayList<>();

      while (tokens.hasMoreElements()) {
         stringList.add(tokens.nextElement().toString());
      }
      return stringList;
   }

   private BufferedReader getFileReader(String inputFileName) throws FileNotFoundException {
      return new BufferedReader(new FileReader(inputFileName));
   }

   private String getInputFileName(String[] argv) throws InvalidInputArgumentException {
      String inputFileName;
      if (argv.length == 1) {
         inputFileName = argv[0];
         LOG.info("Input file: " + inputFileName + " received");
      } else {
         LOG.warning("No input file was provided.");
         throw new InvalidInputArgumentException("Please provide an input file.");
      }
      return inputFileName;
   }
}
