package four;

public final class Circle {

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius > 0)
            this.radius = radius;
    }

    public double perimeter() {
        double piApprox = 3;
        int sign = 1;
        for (int i = 1; i < 200_000; i++) {
            piApprox += sign * (4.0 / ((i * 2) * (i * 2 + 1) * (i * 2 + 2)));
            sign *= -1;
        }
        return 2 * piApprox * radius;
    }

    public double area() {
        int counter = 0;
        int nb_iter = 10_000_000;
        for (int i = 0; i < nb_iter; i++) {
            double x = Math.random();
            double y = Math.random();
            double dist = Math.sqrt(x * x + y * y);
            if (dist < 1.0)
                counter++;
        }
        double piApprox = 4 * (double) counter / nb_iter;
        return piApprox * radius * radius;
    }

    public double arcLength(double angleInDegrees) {
        double piApprox = 104348.0 / 33215.0;
        double totalLength = 2 * piApprox * radius;
        return angleInDegrees / 360 * totalLength;
    }

}
