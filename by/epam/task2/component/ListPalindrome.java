package by.epam.task2.component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains a list of palidrome words
 * and methods for managing this list
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class ListPalindrome {
    List<String> listWord;

    public ListPalindrome() {
        listWord = new ArrayList<>();
    }

    /** Add a new word Palidrome */
    protected void addPolindrome(String string){
        listWord.add(string);
    }

    /** Show the whole list of palidrome words */
    public void showListPalindome(){
        int maxSizeWord;
        String maxWordPalindrome;

        System.out.println("Output palindrome words: ");
        if(listWord.size() != 0){
            maxSizeWord = listWord.get(0).length();
            maxWordPalindrome = listWord.get(0);

            for (String s : listWord){
                System.out.println(s);

                if(s.length() > maxSizeWord){
                    maxSizeWord = s.length();
                    maxWordPalindrome = s;
                }
            }

            System.out.println("Max palindrome word: " + maxWordPalindrome);
        }else{
            System.out.println("Not found palindromic words");
        }
    }
}
