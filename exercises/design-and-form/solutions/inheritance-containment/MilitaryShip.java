import java.util.ArrayList;

// base class for all military ships, which have weapons
// and need to Fire them
// Military ships inheriting from this class can also implement
// specific mission profile interfaces as required by the spec
// A Military ship 'is a' Ship
public abstract class MilitaryShip extends Ship {

  // construct a military ship
  MilitaryShip(float shipMass, float fuelCap, int shipCrew, int pods) {
    super(shipMass, fuelCap, shipCrew, pods);
  }

  public void Fire() {
    // power to weapons, fire!
    for (Weapon w : weapons) {
      w.Fire();
    }
  }

  // ULSN doctrine requires ships with variable weapons loadouts
  public void AddWeapon(Weapon w) {
    weapons.add(w);
  }

  // we use containment to handle weapons on ships
  // A Military ship 'has a' weapon(s), but 'is' not a weapon
  protected ArrayList<Weapon> weapons;

}