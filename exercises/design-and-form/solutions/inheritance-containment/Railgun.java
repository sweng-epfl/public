
// a Railgun 'is a' weapon, but also has contains some ammo
public class Railgun extends Weapon {
  private Ammo ammo;

  public Railgun() {
    ammo = new Ammo(5);
  }

  @Override
  public void Fire() {
    // firing railgun
    if (!ammo.fireRound()) {
      ammo.reload();
      ammo.fireRound();
    }
    System.out.println("firing Railgun!");
  }

}