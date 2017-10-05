package inputUtils;

import entities.PlateauGridPoint;
import entities.Rover;
import enums.CommandType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InputData {
   private PlateauGridPoint leftCorner;
   private PlateauGridPoint rightCorner;
   private List<Rover> roverList;
   private Map<UUID, List<CommandType>> roverCommandsLinkedHashMap = new LinkedHashMap<>();

   public PlateauGridPoint getRightCorner() {
      return rightCorner;
   }

   public void setRightCorner(PlateauGridPoint rightCorner) {
      this.rightCorner = rightCorner;
   }
   public PlateauGridPoint getLeftCorner() {
      return leftCorner;
   }

   public void setLeftCorner(PlateauGridPoint leftCorner) {
      this.leftCorner = leftCorner;
   }

   public List<Rover> getRoverList() {
      return roverList;
   }

   public void setRoverList(List<Rover> roverList) {
      this.roverList = roverList;
   }

   public Map<UUID, List<CommandType>> getRoverCommandsLinkedHashMap() {
      return roverCommandsLinkedHashMap;
   }

   public void setRoverCommandsLinkedHashMap(Map<UUID, List<CommandType>> roverCommandsLinkedHashMap) {
      this.roverCommandsLinkedHashMap = roverCommandsLinkedHashMap;
   }
}
