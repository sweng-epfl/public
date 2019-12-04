# Part 1: Practice [60 points]

In this part of the midterm, you will write two classes. Note that, as is usual in SwEng, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles:

- Write correct and rock-solid code.
- Write tests for your code; aim for 100% line coverage. We provide a sample test case to ease the creation of further tests.
- Follow the SwEng coding conventions, write readable and concise code, and use comments judiciously.

We provide you with a project that can be opened and worked on in Android Studio, and built with Gradle.  
You are free to use any editor of your choice, as long as the code and tests build using `gradlew build`.

**Do not modify in any way the public interface of the code that is given to you**, not even to add checked exceptions. We will use many automated tests when grading; if we cannot build and run your code, you will receive 0 points.

Your code should fail-fast, throwing adequately specific runtime exceptions (or custom versions of these):
- When implementing known interfaces, follow their prescribed exceptions.
- Throw `IndexOutOfBoundsException` for any kind of index whose value is too low or too high.
- Throw `IllegalArgumentException` for arguments (other than indexes) whose value is invalid.
- Throw `IllegalStateException` for operations that fail because of an object's current state.

## Part 1: `EuclideanVector` [30 points]

Implement a `EuclideanVector` class, whose skeleton is in `midterm/src/main/java/ch/epfl/sweng/EuclideanVector.java`. It represents a vector in a Euclidean vector space (in the linear algebra sense) as a sequence of scalars. As you might remember from your math courses, the basis of a vector space is a finite set of vectors B = { b<sub>i</sub> } (with _i_=1...n) that are linearly independent and span the entire vector space. We call _n_ the dimension of the space and of each vector in that space.

Implement the following methods:

- The constructor, which takes an array of integers
- `int value(int index)`, which returns the value of the scalar element at `index` (with `index` starting at 0 not at 1)
- `int dimension()`, which returns the dimension of the vector (i.e., its number of elements)
- `double norm()`, which returns the norm of the vector (i.e., its length in the vector space)
- `boolean isOrthogonalTo(EuclideanVector other)`, which returns true if this vector is orthogonal to the `other` vector, false otherwise
- The `Iterable<Integer>` interface, i.e. the `Iterator<Integer> iterator()` method, which iterates over all values in the vector, in order

The `EuclideanVector` class should be **immutable**: its contents cannot change after having been created.

## Part 2: `PointSpace` [30 points]

Implement a `PointSpace` class, whose skeleton is in `midterm/src/main/java/ch/epfl/sweng/PointSpace.java`. It represents a set of points around an origin point in three-dimensional Euclidean space. 

Implement the following methods:

- The constructor, which takes the coordinates of the `PointSpace`'s origin point
- `int size()`, which returns the number of unique points in the `PointSpace`; the origin is not a point in the point space, unless explicitly added via `addPoint()`
- `void addPoint(int x, int y, int z)`, which adds the point `<x,y,z>` to the `PointSpace`
- `double distanceToClosestPoint()`, which returns the distance between this `PointSpace`'s origin and the point in this `PointSpace` that is closest to said origin
- `double distanceToFarthestPoint()`, which returns the distance between this `PointSpace`'s origin and the point in this `PointSpace` that is farthest away from said origin
- `PointSpace combine(PointSpace other)`, which returns a `PointSpace` consisting of the union of this point space and the `other` point space (the two point spaces must have the same origin). The union is a one-time snapshot, i.e., subsequent changes in the original spaces should not be reflected in the combined space.


