public final class App {
    public static void main(String[] args) {
        System.out.println("Pretend this app implements complex logic using the other classes... :-)");
    }

    public static int getEngineSpeed(int currentSpeed, int distanceToNextCar, boolean isUserParanoid) {
        if (distanceToNextCar < 0) {
            throw new IllegalArgumentException("Distances cannot be negative!");
        }

        int speed = currentSpeed;
        if (currentSpeed > 200) {
            speed = 200;
        }

        if (distanceToNextCar * currentSpeed < 5) {
            speed = speed / 2;
        }

        if (isUserParanoid && speed > 100) {
            speed = 100;
        }

        return speed;
    }

    public enum DiscountKind {NONE, SMALL, LARGE}

    public static int getCarPrice(int originalPrice, DiscountKind discount, boolean isFrequentCustomer) {
        if (discount == DiscountKind.LARGE && !isFrequentCustomer) {
            throw new IllegalArgumentException("Non-frequent customers cannot get large discounts.");
        }

        if (isFrequentCustomer) {
            switch (discount) {
                case LARGE:
                    return originalPrice / 100 * 60;
                case SMALL:
                    return originalPrice / 100 * 80;
                default:
                    return originalPrice;
            }
        }

        switch (discount) {
            case LARGE:
                return originalPrice / 100 * 70;
            case SMALL:
                return originalPrice / 100 * 90;
            default:
                return originalPrice;
        }
    }
}
