
import java.util.List;

// base class for all civilian ships, which do not have weapons
// Civilian ships inheriting from this class can also implement
// specific mission profile interfaces as required by the spec
// A CivilianShip 'is a' Ship
public abstract class CivilianShip extends Ship {

  // construct a Civilian ship
  public CivilianShip(float shipMass, float fuelCap, int shipCrew, int pods) {
    super(shipMass, fuelCap, shipCrew, pods);
  }

  @Override
  public List<CargoPod> unload() {
    System.out.println("CivShip Processing manifest and unloading ...");
    return super.unload();
  }
}