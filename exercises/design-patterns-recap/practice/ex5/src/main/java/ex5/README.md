# Exercise 5

Your application allows users to color primitive shapes, such as circles, triangles,
and rectangles (look at the `App` class).
You decide to introduce more complicated shapes in your system that could consist of multiple primitive shapes
and even additional complicated shapes. For
instance, you might have a shape that consists of 3 elements, two rectangles and one more shape that itself
contains 2 elements, a triangle and a circle.


Use the **composite design pattern** (check the `App` class) so you can have code as follows:

```Java
         Shape innerShape = new ShapeComposite();
         innerShape.add(new Triangle());
         innerShape.add(new Triangle());
         Shape shape = new ShapeComposite();
         shape.add(new Circle());
         shape.add(new Rectangle());
         shape.add(innerShape);
```

 and when we do `shape.color("black")`, all the shapes belonging to `shape` will be colored black.

