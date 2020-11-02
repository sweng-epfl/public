# Software Engineering - Homework 1

## Part 2: Practice [78 points]

In this part of the exam you need to write real code. As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles. You should write code that is:

- Correct and robust
- Readable and concise
- Judiciously commented
- Write tests for your code and aim for 100% line coverage; we provide a sample test case to ease the creation of further tests.

We provide you with a project that can be opened and worked on in Android Studio, and built with Gradle.
You are free to use any editor of your choice, as long as the code and tests build using `./gradlew build`.

**Do not modify in any way the public interface of the code that is given to you**, not even to add checked exceptions.
We will use many automated tests when grading; if we cannot build and run your code, you will receive 0 points.

Your code should fail cleanly when it cannot continue safely, and throw adequately specific runtime exceptions (or custom versions of these):

- When implementing known interfaces, follow their prescribed exceptions
- Throw `IndexOutOfBoundsException` for any kind of index whose value is too low or too high
- Throw `IllegalArgumentException` for arguments (other than indexes) whose value is invalid
- Throw `IllegalStateException` for operations that fail because of an object's current state

You are allowed to add methods (```public``` and ```private```) if you need but you must not remove any of the provided ones. That being said, you will have to write tests for all your code (see below) so be careful!

Please remember that we will only grade whatever is in your `master` branch. We will use `./gradlew build` to build your code. And, again, if we cannot build and run your code, you will receive 0 points.

## Part 2.1: `EuclideanVector` [46 points]

Implement a `EuclideanVector` class, whose skeleton is in `src/main/java/homework01/EuclideanVector.java`.
It represents a vector in a Euclidean vector space (in the linear algebra sense) as a sequence of scalars.
As you might remember from your math courses, the basis of a vector space is a finite set of vectors B = {} or { b<sub>i</sub> } (with _i_=1...n) that are linearly independent and span the entire vector space. 
We call the integer _n_ ≥ 0 the dimension of the space (that is the dimension of each vector in that space).

Implement the following methods:

- The constructor, which takes an array of integers
- `int value(int index)`, which returns the value of the scalar element at `index` (with `index` starting at 1, just as the mathematical vectors)
- `int dimension()`, which returns the dimension of the vector (i.e., its number of elements)
- `double norm()`, which returns the norm of the vector (i.e., its length in the vector space)
- `boolean isOrthogonalTo(EuclideanVector other)`, which returns true if this vector is orthogonal to the `other` vector, false otherwise
- `EuclideanVector crossProduct(EuclideanVector other)`, which returns the cross-product between this vector and the `other` vector (you can check the definition of the cross-product [here](https://en.wikipedia.org/wiki/Cross_product). We will assume that the cross-product is defined only in dimension 3).
- `EuclideanVector plus(EuclideanVector other)`, which computes the addition, component by component, of this vector and the `other` (it is only defined for two vectors of the same dimension).
- `EuclideanVector scalarDivide(double scalar)`, which computes the division by the `scalar` of this vector. The result is rounded to the nearest integer, according to the round half towards positive infinity rule.
- The `Iterable<Integer>` interface, i.e. the `Iterator<Integer> iterator()` method, which iterates over all values in the vector, in order
- `equals` and `hashcode` that override the usual methods of the Java `Object` class.

The `EuclideanVector` class should be **immutable**: its contents cannot change after having been created.

## Part 2.2: `PointSpace` [22 points]

Implement a `PointSpace` class, whose skeleton is in `src/main/java/homework01/PointSpace.java`. It represents a set of points around an origin point in three-dimensional Euclidean space. You may reuse the `EuclideanVector` class if you feel the need to.

Implement the following methods:

- The constructor, which takes the coordinates of the `PointSpace`'s origin point
- `int size()`, which returns the number of unique points in the `PointSpace`; the origin is not a point in the point space, unless explicitly added via `addPoint()`
- `void addPoint(int x, int y, int z)`, which adds the point with absolute coordinates `<x,y,z>` to the `PointSpace`.
- `double distanceToClosestPoint()`, which returns the distance between this `PointSpace`'s origin and the point in this `PointSpace` that is closest to said origin
- `double distanceToFarthestPoint()`, which returns the distance between this `PointSpace`'s origin and the point in this `PointSpace` that is farthest away from said origin
- `PointSpace combine(PointSpace other)`, which returns a `PointSpace` consisting of the union of this point space and the `other` point space (the two point spaces must have the same origin).
  The union is a one-time snapshot, i.e., subsequent changes in the original spaces should not be reflected in the combined space.
- `EuclideanVector geometricCenter()`, which returns an approximation of the geometric center (as the `EuclideanVector` has components of type `int`) of this `PointSpace`, in absolute coordinates (see [here](https://en.wikipedia.org/wiki/Centroid) for details). If the `PointSpace` contains no point, the geometric center is not defined (i.e. it should throw an exception).

## Part 2.3: Coverage [10 points]

By this point you have implemented all the functionality required for this homework. However, as a good SwEng student, you know that implementing new functionality is only part of the job. It is now time to check the robustness of your code by writing tests.

Your final task is to write tests that achieve **100% statement coverage** on all code in the sweng.epfl.ch package, including code written by you.

Grading: You will receive 0 points for Part 2.3 if coverage is below 85%. You will receive 10 points if your coverage is 100%. In between these two, the number of points will be distributed according to an exponential curve that captures the fact that, the higher the coverage, the harder it is to improve it.

Remember that you can run `./gradlew build`, after which you can find test results in `build/reports/tests/test/index.html` and coverage results in `build/reports/coverage/index.html`. On Windows, use `gradlew.bat` instead of `./gradlew`.
