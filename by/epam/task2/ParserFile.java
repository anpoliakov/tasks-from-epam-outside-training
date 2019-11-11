package by.epam.task2;

import by.epam.task2.component.ListPalindrome;
import by.epam.task2.component.Sentence;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  This Class parses the received file into sentences,
 *  show list of palidrome words
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 */
public class ParserFile {
    private Scanner scanner;
    private List <Sentence> text; // Contains all sentences
    private ListPalindrome listPalindrome; // Сlass for controlling palindrome words


    public ParserFile (String pathToFile) {
        text = new ArrayList<>();
        listPalindrome = new ListPalindrome();

        try { //Get Scanner file
            scanner = new Scanner(new File(pathToFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //start parser
        pars();
    }

    /** This method parses a file into sentences and forms text on their basis */
    private void pars(){
        Sentence sentence;

        while (scanner.hasNext()){
            sentence = new Sentence();
            sentence.setListPalindrome(listPalindrome);

            sentence.read(scanner);

            // Check - empty sentence ?
            if(!sentence.isEmptyListSentences()){
                text.add(sentence);
            }
        }

        // close the scanner after work
        scanner.close();
    }

    /** Shows all palidrome words in the text and the biggest word palidrome  */
    public void showPolindrome(){
        listPalindrome.showListPalindome();
    }
}
