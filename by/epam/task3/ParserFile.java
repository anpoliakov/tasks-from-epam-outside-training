package by.epam.task3;

import by.epam.task3.details.Intersection;
import by.epam.task3.details.Line;
import by.epam.task3.details.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class processes the file, instantiates points, lines, and collects intersection information.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class ParserFile {
    //path to file
    private String pathToFile;
    //saves two points and passes them to create a line
    private List <Point> listForTwoPoints;
    //collection stores all lines
    private List <Line> listLines;
    //stores current intersection of lines
    Intersection intersection;
    //сollection contains information about the abscissa and which lines it belongs
    TreeMap <Double,Intersection> collectionIntersectionLines;
    private Scanner scanner;

    private final int SELL_VALUE_X = 0; //X position in file
    private final int SELL_VALUE_Y = 1; //Y position in file
    private final int NUMBER_POINTS_FOR_LINE = 2; //required amount Points for create line

    public ParserFile(String pathToFile ) {
        this.pathToFile = pathToFile;
        //launch parsing
        startParser();
    }

    /**
     * this method creates necessary for work collections and
     * create lines on based points in the list
     *
     * */
    private void startParser(){
        try {
            scanner = new Scanner(new File(pathToFile));
            listForTwoPoints = new ArrayList<>();
            listLines = new ArrayList<>();
            //set default sort
            collectionIntersectionLines = new TreeMap<>(new Comparator<Double>() {
                @Override
                public int compare(Double aDouble, Double t1) {
                    return aDouble.compareTo(t1);
                }
            });

            //while file has lines read them
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String [] masXY = line.split(";");
                Double y = Double.parseDouble(masXY[SELL_VALUE_X]);
                Double x = Double.parseDouble(masXY[SELL_VALUE_Y]);

                listForTwoPoints.add(new Point(x,y));

                //if have 2 points => create line
                if(listForTwoPoints.size() == NUMBER_POINTS_FOR_LINE ){
                    Point onePoint = listForTwoPoints.get(0);
                    Point twoPoint = listForTwoPoints.get(1);
                    Line newLine = new Line (onePoint,twoPoint);
                    listLines.add(newLine);
                    //clean again
                    listForTwoPoints.clear();
                }
            }

            //run the method for processing intersections lines
            findIntersections();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method trying to create intersections lines
     * If any lines crossed adds intersection to TreeMap and key for sorting
     * */
    private void findIntersections(){
        for(int i = 0; i < listLines.size()-1; i++){
            for (int j = 0 + i + 1; j < listLines.size(); j++){
                Line lineOne = listLines.get(i);
                Line lineTwo = listLines.get(j);
                intersection = new Intersection(lineOne,lineTwo);

                //if intersection is found
                if(intersection.checkIntersectionOfTwoLineSegments()){
                    collectionIntersectionLines.put(intersection.getValueAbscissa(),intersection);
                }
            }
        }
    }

    /**
     * this method to display information about the smallest abscissa
     * and about lines on the basis of which is built
     * */
    public void showMinimumAbscissa(){
        Map.Entry<Double,Intersection> entry = collectionIntersectionLines.firstEntry();
        System.out.println(entry.getValue().getInfoLines());
        System.out.println("\nMinimum abscissa = " + entry.getKey());
    }







}
