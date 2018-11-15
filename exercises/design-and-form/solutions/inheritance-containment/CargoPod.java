import java.util.ArrayList;

public class CargoPod {
  private ArrayList<CargoItem> items;

  public CargoPod() {
    items = new ArrayList<CargoItem>();
  }

  // add/remove items in cargo, etc. 
  public void AddItem(CargoItem item) {
    items.add(item);
  }
}