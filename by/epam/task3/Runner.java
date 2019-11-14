package by.epam.task3;

/**
 * This class and method "main" start the program.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class Runner {
    public static void main(String[] args) {

        //File contains point values.
        final String PATH_TO_FILE = "src/by/epam/task3/source/fileSourcePoints.txt";

        //Сreate parser and run it
        ParserFile parserFile = new ParserFile(PATH_TO_FILE);

        //Show lines by which found smallest abscissa
        parserFile.showMinimumAbscissa();
    }
}
