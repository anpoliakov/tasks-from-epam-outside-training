package by.epam.task2;

import by.epam.task2.constants.Constants;

/**
 * The class is designed to run program.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class Runner {
    public static void main(String[] args) {

        // Create parser and pass path to file
        ParserFile parserSentence = new ParserFile(Constants.PATH_TO_FILE);

        //Show palindrome words
        parserSentence.showPolindrome();

    }
}
