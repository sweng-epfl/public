
import java.util.ArrayList;

// mid size ship with heavy weapons that can patrol and make 
// deliveries
public class Destroyer extends MilitaryShip implements PatrolMissionProfile, DeliveryMissionProfile {

  public Destroyer() {
    super(10000, 1000, 100, 50);
    // destroyers have a decent loadout
    weapons.add(new Missile());
    weapons.add(new PlasmaCannon());
    weapons.add(new PlasmaCannon());
    weapons.add(new PlasmaCannon());
  }

  @Override
  public void orderPatrol(ArrayList<Integer> sectors) {
    System.out.println("Destroyer on patrol mission");
  }

  @Override
  public void orderDelivery(int sourceSector, int destSector) {
    System.out.println("Destroyer on delivery mission");
  }
}