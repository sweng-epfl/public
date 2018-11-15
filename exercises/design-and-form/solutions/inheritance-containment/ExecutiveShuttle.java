
// high ranking officers and dignitaries travel in style ...
// able to execute ferry missions and go autonomous
public class ExecutiveShuttle extends CivilianShip implements FerryMissionProfile, AutonomousShip {

  public ExecutiveShuttle() {
    super(500, 500, 3, 1);
  }

  @Override
  public void orderFerry(int sourceSector, int destSector) {
    System.out.println("Shuttle on ferry mission");
  }

  @Override
  public void EnterAutonomousMode() {
    System.out.println("Shuttle entering autonomous mode");
  }
}