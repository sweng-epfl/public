package ex5;

import java.util.LinkedList;
import java.util.List;

public class ShapeComposite extends Shape {
    private List<Shape> shapes;

    public ShapeComposite() {
        shapes = new LinkedList<>();
    }

    @Override
    public void add(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public void color(String color) {
        for (Shape s: shapes) {
            s.color(color);
        }
    }
}
