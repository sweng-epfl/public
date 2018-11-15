
// we define an abstract weapon that specific 
// weapons can inherit from. This makes it easy for a 
// Ship to have different weapons and manage them
public abstract class Weapon {
  // different weapons must implement Fire
  abstract public void Fire();
}
