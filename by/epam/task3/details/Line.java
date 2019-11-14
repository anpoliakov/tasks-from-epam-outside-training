package by.epam.task3.details;

/**
 * This class takes 2 points and builds 1 line
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 */
public class Line {
    private Point oneTerminationLine;
    private Point twoTerminationLine;

    public Line(Point oneTerminationLine, Point twoTerminationLine) {
        this.oneTerminationLine = oneTerminationLine;
        this.twoTerminationLine = twoTerminationLine;
    }

    //Displays information on which points built the line
    @Override
    public String toString() {
        return "Lines upbuild by points:\n" + "One point: X = " + oneTerminationLine.getX() + " | Y = " + oneTerminationLine.getY() +
                "\nTwo point: X = " + twoTerminationLine.getX() + " | Y = " + twoTerminationLine.getY();
    }

    public Point getOneTerminationLine() {
        return oneTerminationLine;
    }

    public Point getTwoTerminationLine() {
        return twoTerminationLine;
    }
}
