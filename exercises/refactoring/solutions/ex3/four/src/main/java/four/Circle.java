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
        return 2 * approximatePI() * radius;
    }

    public double area() {
        return approximatePI() * radius * radius;
    }

    public double arcLength(double angleInDegrees) {
        return angleInDegrees / 360 * perimeter();
    }

    private double approximatePI() {
        return 104348.0 / 33215.0;
    }

}