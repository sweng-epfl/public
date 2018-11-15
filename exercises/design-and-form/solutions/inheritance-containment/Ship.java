import java.util.List;
import java.util.ArrayList;

// Ship forms a base class for all ships in the ULSN fleet
// we use an inheritance hierarchy for ships because they have some 
// core similarities, but also specific functions per ship class
// and different required mission profiles
public abstract class Ship {
  public enum Paint {
    GRAY, BLUE
  }

  protected Paint PaintJob;

  // the cargo bay stores pods as per the spec
  // we use containment, because cargo pods and items are simple objects
  // that have no complex requirements.
  // A Ship cargo bay 'has a' Pod (or more) and a Pod 'has a' CargoItem (one or
  // more)
  // protected because inherited classes need access to the cargo bay
  protected List<CargoPod> cargoBay;
  private float mass;
  private float fuelCapacity;
  private int crew;
  private int maxPods;


  public void Load(CargoPod pod) {
    if (cargoBay.size() < maxPods) {
      cargoBay.add(pod);
    } else {
      System.out.println("Cargo bay full");
    }
  }

  // unload cargo and clear the bay
  public List<CargoPod> unload() {
    List<CargoPod> c = cargoBay;
    cargoBay = new ArrayList<CargoPod>();
    return c;
  }

  Ship(float shipMass, float fuelCap, int shipCrew, int pods) {
    mass = shipMass;
    fuelCapacity = fuelCap;
    crew = shipCrew;
    maxPods = pods;
    cargoBay = new ArrayList<CargoPod>();
  }

}