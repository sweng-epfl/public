import java.util.ArrayList;

// ULSN primary heavy support vessel
// able to seek and destroy, and patrol
public class Battleship extends MilitaryShip implements SearchAndDestroyMissionProfile, PatrolMissionProfile {

  Battleship() {
    super(60000, 5000, 1000, 400);
    // battleships have a decent armament
    weapons.add(new Railgun());
    weapons.add(new Railgun());
    weapons.add(new Railgun());
    weapons.add(new Railgun());
    weapons.add(new Missile());
    weapons.add(new PlasmaCannon());
    weapons.add(new PlasmaCannon());
    weapons.add(new PlasmaCannon());
  }

  @Override
  public void orderSeachAndDestroy(int sector) {
    System.out.println("Battleship on search and destroy mission");
  }

  @Override
  public void orderPatrol(ArrayList<Integer> sectors) {
    System.out.println("Battleship on patrol mission");
  }
}