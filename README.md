Mars Rover Simple Simulator
==============

A rover's position and location is represented by a combination of x and y coordinates and a letter representing one of
the four cardinal compass points. The plateau is divided up into a grid to simplify navigation. An example position 
might be 0, 0, N, which means the rover is in the bottom left corner and facing North.
 
The rover is controlled by a simple string of letters. 
The possible letters are 'L', 'R' and 'M'. 'L' and 'R' makes the rover spin 90 degrees left or right respectively, 
without moving from its current spot. 'M' means move forward one grid point, and maintain the same heading.
    
Input Type | Format | Description |
--- | --- | --- |
UPPER_RIGHT_HAND_CORNER_COORDINATES | (x, y) | Determines the size of the grid. This must be on the first line and can only be specified once |
INITIAL_ROVER_POSITION | (x, y, [N, S, E, W]) | Rover's starting position |
ROVER_COMMAND_STRING | [L, R, M] | Movement commands for the corresponding rover. This input has to follow a rover initial placement input. One can specify as many rovers and rover commands as needed|

Sample Input Example
---
    5 5
    1 2 N
    LMLMLMLMM
    3 3 E
    MMRMMRMRRM
        
Build & Run
---
To compile and build, run the following command at the root folder

    mvn clean install