package ch.epfl.sweng;

import java.util.HashSet;
import java.util.Set;

public final class PointSpace {
    private final EuclideanVector origin;
    private final Set<EuclideanVector> points;
    private double distanceToClosest;
    private double distanceToFarthest;


    public PointSpace(final int originX, final int originY, final int originZ) {
        origin = new EuclideanVector(originX, originY, originZ);
        points = new HashSet<>();

        // Keeping the distances as fields allows for simpler code,
        // and also much faster access to them since there is no need
        // to recompute them every time.
        // (There is a trade-off: the class uses a little bit more memory.
        //  If this library caused memory problems in an app,
        //  it might be necessary to reconsider this decision.)

        // It's important to initialize those to the proper values;
        // setting distanceToClosest to 0 would keep it 0 forever
        // since it is updated by min()
        distanceToClosest = Double.MAX_VALUE;
        distanceToFarthest = 0;
    }

    // Private constructor used in combine(), which directly sets all fields
    private PointSpace(final EuclideanVector origin, final Set<EuclideanVector> points,
                       final double distanceToClosest, final double distanceToFarthest) {
        this.origin = origin;
        this.points = points;
        this.distanceToClosest = distanceToClosest;
        this.distanceToFarthest = distanceToFarthest;
    }


    public int size() {
        return points.size();
    }

    public void addPoint(final int x, final int y, final int z) {
        // Reuse EuclideanVector here, since it can trivially implement equality,
        // which is necessary to avoid duplicates.
        // And no need to rewrite a distance function, we already have one!
        final EuclideanVector point = new EuclideanVector(
                x - origin.value(0),
                y - origin.value(1),
                z - origin.value(2)
        );
        points.add(point);

        final double distance = point.norm();
        distanceToClosest = Math.min(distanceToClosest, distance);
        distanceToFarthest = Math.max(distanceToFarthest, distance);
    }

    public double distanceToClosestPoint() {
        ensureNotEmpty();

        return distanceToClosest;
    }

    public double distanceToFarthestPoint() {
        ensureNotEmpty();

        return distanceToFarthest;
    }

    public PointSpace combine(final PointSpace other) {
        if (other == null) {
            throw new IllegalArgumentException("'other' must not be null.");
        }
        if (!origin.equals(other.origin)) {
            throw new IllegalArgumentException("'other' must have the same origin.");
        }

        final Set<EuclideanVector> allPoints = new HashSet<>();
        allPoints.addAll(points);
        allPoints.addAll(other.points);

        return new PointSpace(
                origin,
                allPoints,
                Math.min(distanceToClosest, other.distanceToClosest),
                Math.max(distanceToFarthest, other.distanceToFarthest)
        );
    }


    // Helper method to centralize validation for methods that require a non-empty space.
    private void ensureNotEmpty() {
        if (size() == 0) {
            throw new IllegalStateException("No points have been added.");
        }
    }
}
