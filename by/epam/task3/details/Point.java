package by.epam.task3.details;

/**
 * This class represents a point on a coordinate line.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
*/
public class Point {

    private double y; // x coordinate
    private double x; // y coordinate

    public Point(double y, double x) {
        this.y = y;
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}
