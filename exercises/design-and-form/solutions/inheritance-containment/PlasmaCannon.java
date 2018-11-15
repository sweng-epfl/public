
// a PlasmaCannon 'is a' weapon, but does not contain any ammo
// because it draws energy directly from the Ship's reactor 
public class PlasmaCannon extends Weapon {
  @Override
  public void Fire() {
    // firing plasmaCannon -- no ammo to worry about
    System.out.println("firing plasma cannons");
  }
}