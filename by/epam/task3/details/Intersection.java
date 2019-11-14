package by.epam.task3.details;

/**
 * This class works with intersections of two lines.
 * Сontains main method for handling intersection and obtaining the abscissa value.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class Intersection {
    //points on which built 2 lines
    private Point p1,p2,p3,p4;
    //transmitted lines to the object
    private Line lineOne,lineTwo;
    //to change position of names
    private Point swapInterpose;
    //stores result of calculation abscissas
    private Double valueAbscissa;

    public Intersection(Line lineOne, Line lineTwo) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        p1 = lineOne.getOneTerminationLine();
        p2 = lineOne.getTwoTerminationLine();
        p3 = lineTwo.getOneTerminationLine();
        p4 = lineTwo.getTwoTerminationLine();
    }

    //displays information about lines on which built the intersection
    public String getInfoLines() {
        return lineOne + "\n" + lineTwo;
    }

    //to get value about abscissa
    public Double getValueAbscissa() {
        return valueAbscissa;
    }

    /**
     * Main method for checking intersection of lines and definition of abscissas
     * */
    public boolean checkIntersectionOfTwoLineSegments(){
        boolean flag = true;

        //set points in the correct position
        if (p2.getX() < p1.getX()) {
            swapInterpose = p1;
            p1 = p2;
            p2 = swapInterpose;
        }

        //set points in the correct position
        if (p4.getX() < p3.getX()) {
            swapInterpose = p3;
            p3 = p4;
            p4 = swapInterpose;
        }

        if (p2.getX() < p3.getX()) {
            flag = false;
        }

        //if both segments are vertical
        if ((p1.getX() - p2.getX() == 0) && (p3.getX() - p4.getX() == 0)) {

            //if they lie on one X
            if (p1.getX() == p3.getX()) {

                //check are they intersect ?
                if (!((Math.max(p1.getY(), p2.getY()) < Math.min(p3.getY(), p4.getY())) ||
                        (Math.min(p1.getY(), p2.getY()) > Math.max(p3.getY(), p4.getY())))) {
                    flag = true;
                }
            }
            flag = false;
        }

        //f1(x) = A1*x + b1 = y
        //f2(x) = A2*x + b2 = y

        //special case if the first segment is vertical
        if (p1.getX() - p2.getX() == 0) {

            // find Xa, Ya - intersection points of two lines
            double Xa = p1.getX();
            double A2 = (p3.getY() - p4.getY()) / (p3.getX() - p4.getX());
            double b2 = p3.getY() - A2 * p3.getX();
            double Ya = A2 * Xa + b2;
            valueAbscissa = Xa;

            if (p3.getX() <= Xa && p4.getX() >= Xa && Math.min(p1.getY(), p2.getY()) <= Ya &&
                    Math.max(p1.getY(), p2.getY()) >= Ya) {
                flag = true;
            }
        }

        //special case if the second segment is vertical
        if (p3.getX() - p4.getX() == 0) {

            // find Xa, Ya - intersection points of two lines
            double Xa = p3.getX();
            double A1 = (p1.getY() - p2.getY() ) / (p1.getX() - p2.getX());
            double b1 = p1.getY()  - A1 * p1.getX();
            double Ya = A1 * Xa + b1;
            valueAbscissa = Xa;

            if (p1.getX() <= Xa && p2.getX() >= Xa && Math.min(p3.getY() , p4.getY() ) <= Ya &&
                    Math.max(p3.getY() , p4.getY() ) >= Ya) {
                flag = true;
            }
        }

        //if both segments aren't vertical
        double A1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double A2 = (p3.getY() - p4.getY()) / (p3.getX() - p4.getX());
        double b1 = p1.getY() - A1 * p1.getX();
        double b2 = p3.getY() - A2 * p3.getX();

        if (A1 == A2) {
            flag = false; // segments are parallel
        }

        // Xa - abscissa of the intersection point of two lines
        double Xa = (b2 - b1) / (A1 - A2);
        valueAbscissa = Xa;

        if ((Xa < Math.max(p1.getX(), p3.getX())) || (Xa > Math.min( p2.getX(), p4.getX()))) {
            flag = false; // point Xa is outside the intersection of projections of the segments on the X axis (?not correct)
        } else {
            flag = true;
        }

        return flag;
    }
}
