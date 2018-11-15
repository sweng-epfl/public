
import java.util.ArrayList;

// small flexible ship that can go autonomous, patrol, deliver, and reconnoiter
public class Corvette extends MilitaryShip
    implements PatrolMissionProfile, DeliveryMissionProfile, ReconaissanceMissionProfile, AutonomousShip {

  public Corvette() {
    super(5000, 500, 50, 10);
    // corvettes have few weapons
    weapons.add(new PlasmaCannon());
    weapons.add(new PlasmaCannon());
  }

  @Override
  public void orderPatrol(ArrayList<Integer> sectors) {
    System.out.println("Corvette on patrol mission");
  }

  @Override
  public void orderDelivery(int sourceSector, int destSector) {
    System.out.println("Corvette on delivery mission");
  }

  @Override
  public void orderReconaissance(int sector) {
    System.out.println("Corvette on reconaissance mission");
  }

  @Override
  public void EnterAutonomousMode() {
    System.out.println("Corvette entering autonomous mode");
  }
}