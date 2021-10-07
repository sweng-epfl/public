package ch.epfl.sweng.locator;

public class DeviceNameQuery implements DeviceQuery<String> {

    public String query() {
        return "system:hal9000";
    }

}
