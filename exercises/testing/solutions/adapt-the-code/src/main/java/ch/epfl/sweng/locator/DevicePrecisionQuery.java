package ch.epfl.sweng.locator;

import java.util.List;
import java.util.ArrayList;

public class DevicePrecisionQuery implements DeviceQuery<List<Precision>> {

    public List<Precision> query() {
        DeviceNameQuery nameQuery = new DeviceNameQuery();
        String name = nameQuery.query();

        List<Precision> result = new ArrayList<>();
        result.add(Precision.COARSE);
        result.add(Precision.FINE);

        if (name.contains("hal9000")) {
            result.remove(1);
        }

        return result;
    }
}
