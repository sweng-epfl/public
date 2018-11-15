
import java.util.ArrayList;

// a well armed and flexible warship
// that can search and destroy, patrol and reconnoiter
public class Frigate extends MilitaryShip
    implements SearchAndDestroyMissionProfile, PatrolMissionProfile, ReconaissanceMissionProfile {

  public Frigate() {
    super(30000, 3000, 400, 100);
    // frigates have fewer weapons that battleships
    weapons.add(new Railgun());
    weapons.add(new Railgun());
    weapons.add(new Missile());
    weapons.add(new PlasmaCannon());
  }

  @Override
  public void orderSeachAndDestroy(int sector) {
    System.out.println("Frigate on search and destroy mission");
  }

  @Override
  public void orderPatrol(ArrayList<Integer> sectors) {
    System.out.println("Frigate on patrol mission");
  }

  @Override
  public void orderReconaissance(int sector) {
    System.out.println("Frigate on reconaissance mission");
  }
}