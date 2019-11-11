package by.epam.task2.component;

import by.epam.task2.interfaces.PartOfSentence;

/**
 * This class represents a word and has a method for checking Palindrome
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class Word implements PartOfSentence {
    private String word;

    protected Word(String word) {
        this.word = word;
    }

    protected void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    /** This method checks if the passed word is Palindrome */
    protected boolean isPalindrome(String stringOriginal){
        boolean flag = false;

        String stringRevers = reversWord(stringOriginal);
        if(stringOriginal.length() > 1 && (stringOriginal.equalsIgnoreCase(stringRevers))){
            flag = true;
        }
        return flag;
    }

    /** This Method turns string */
    private String reversWord(String stringOriginal){
        StringBuffer stringBufferOriginal = new StringBuffer(stringOriginal);
        return ( new String (stringBufferOriginal.reverse()));
    }

    /** Method checks if the string is a palindrome if yes - add to the list of palidromes */
    protected void checkPalindrome(ListPalindrome listPalindrome) {
        if(isPalindrome(this.word)){
            listPalindrome.addPolindrome(word);
        }
    }
}
