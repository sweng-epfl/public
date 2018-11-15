
// move goods and assets around the galaxy
// able to execute delivery missions and go autonomous
public class Freighter extends CivilianShip implements DeliveryMissionProfile, AutonomousShip {

  public Freighter() {
    super(40000, 4000, 50, 1000);
  }

  @Override
  public void orderDelivery(int sourceSector, int destSector) {
    System.out.println("Freighter on delivery mission");
  }

  @Override
  public void EnterAutonomousMode() {
    System.out.println("Freighter entering autonomous mode");
  }
}