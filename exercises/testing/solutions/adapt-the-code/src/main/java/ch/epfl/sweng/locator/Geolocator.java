package ch.epfl.sweng.locator;

public final class Geolocator {

    private Precision precision;
    private boolean isRunning = false;
    private HardwareSupportService supportService;

    public Geolocator(Precision precision) {
        this.precision = precision;
        this.supportService = new HardwareSupportService();
    }

    public boolean isLocationServiceRunning() {
        return this.isRunning;
    }

    public void startLocationService() throws Exception {
        isRunning = true;

        HardwareSupportService.Info supportInfo = supportService.queryInfo();

        if (supportInfo.versionMajor < 2 && supportInfo.versionMinor <= 1) {
            throw new UnsupportedOperationException("Device id \"" + supportInfo.id + "\" is out of date!");
        }

        if (!supportInfo.hasFinegrainSupport && precision == Precision.FINE) {
            precision = Precision.COARSE;
        }
        if (!supportInfo.hasCoarseSupport && precision == Precision.COARSE) {
            throw new IllegalStateException("Device does not have location support");
        }
    }

    public PositionRange getUserPosition() {
        if (!isRunning) {
            throw new IllegalStateException();
        }

        switch(precision) {
            case FINE: {
                return new PositionRange(
                        46.506373,
                        46.526945,
                        6.461957,
                        6.666246
                );
            }
            case COARSE: {
                return new PositionRange(
                        47.305982,
                        47.506745,
                        5.201852,
                        5.691859
                );
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }
    }
}
