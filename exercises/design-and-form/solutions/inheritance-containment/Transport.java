
// Move the ULSNs marines and officers around the galaxy
// able to execute delivery and ferry missions
public class Transport extends CivilianShip implements DeliveryMissionProfile, FerryMissionProfile {

    public Transport() {
      super(30000, 3500, 150, 50);
    }
    
    @Override
    public void orderDelivery(int sourceSector, int destSector) {
      System.out.println("Transport on delivery mission");
    }
    @Override
    public void orderFerry(int sourceSector, int destSector) {
      System.out.println("Transport on ferry mission");
    }
}
