
// the Ammo class representes any type of ammo needed
// Weapons with ammo should contain an Ammo instance
public class Ammo {
  private int maxRounds;
  private int currentRounds;

  public Ammo(int maxRounds) {
    this.maxRounds = maxRounds;
    this.currentRounds = maxRounds;
  }

  // returns true if enough ammo to fire
  public boolean fireRound() {
    if (currentRounds > 0) {
      currentRounds--;
      return true;
    } else {
      // need to reload
      return false;
    }
  }

  public int rounds() {
    return currentRounds;
  }

  public void reload() {
    currentRounds = maxRounds;
  }
}