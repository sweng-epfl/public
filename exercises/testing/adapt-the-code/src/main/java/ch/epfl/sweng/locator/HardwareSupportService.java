package ch.epfl.sweng.locator;

import java.util.List;

public class HardwareSupportService {

    public static final class Info {
        public String id;
        public int versionMajor;
        public int versionMinor;
        public int versionRevision;

        public boolean hasFinegrainSupport;
        public boolean hasCoarseSupport;
    }

    public Info queryInfo() {
        Info info = new Info();

        info.id = getDeviceId();

        int[] version = getVersion();
        info.versionMajor = version[0];
        info.versionMinor = version[1];
        info.versionRevision = version[2];

        DevicePrecisionQuery precisionQuery = new DevicePrecisionQuery();
        List<Precision> supportedPrecision = precisionQuery.query();

        info.hasFinegrainSupport = supportedPrecision.contains(Precision.FINE);
        info.hasCoarseSupport = supportedPrecision.contains(Precision.COARSE);

        return info;
    }

    private int[] getVersion() {
        return new int[]{2, 1, 0};
    }

    private String getDeviceId() {
        return "ASO2001";
    }
}
