

// a Missile 'is a' weapon, but also contains some ammo
public class Missile extends Weapon {

  private Ammo ammo;

  public Missile() { 
    ammo = new Ammo(2);
  }

  @Override
  public void Fire() {
    // firing missiles
    if (!ammo.fireRound()) {
      ammo.reload();
      ammo.fireRound();
    }
    System.out.println("firing missile!");
  }
}