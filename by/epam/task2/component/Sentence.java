package by.epam.task2.component;

import by.epam.task2.interfaces.PartOfSentence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * This class represents a sentence which consists of a list of words
 * and punctuation marks (both implement SentenceObject).
 * Provides methods to read sentence, add elements to the sentence, iterator.
 *
 * */
public class Sentence implements Iterable <PartOfSentence> {
    private List <PartOfSentence> sentence;
    private ListPalindrome listPalindrome;
    private Word objectWord = null;

    public Sentence() {
        sentence = new ArrayList<>();
    }

    /** The method loops through all the words in the sentence and processes it */
    public void read(Scanner scanner){
        boolean endOfSentence = false; // flag end sentence
        String word = null; // current word
        int wordLength = 0; // length of current word
        PunctuationMark punctuationMark = null; // contains punctuation mark or null

        do {
            if (scanner.hasNext()) {
                word = scanner.next();
                wordLength = word.length();

                // check if the last character of a word is a punctuation mark
                punctuationMark = PunctuationMark.createPunctuationMark(word.charAt(wordLength - 1));
                objectWord = new Word(word); // create object of current word

                if (punctuationMark == null) { // if last word has no punctuation mark
                    sentence.add(objectWord);
                    objectWord.checkPalindrome(listPalindrome); //Checks current object word on palindrome

                } else { // has a punctuation mark
                    if (punctuationMark.isEndSentence()) {
                        endOfSentence = true;
                    }

                    sentence.add(objectWord); //было sentence.add(new Word(word));
                    objectWord = new Word(word.substring(0,wordLength-1)); //cut off punctuation mark
                    objectWord.checkPalindrome(listPalindrome);
                }

            } else {
                endOfSentence = true;
            }

        }while (!endOfSentence);
    }

    /** Checks if an empty sentence */
    public boolean isEmptyListSentences(){
        boolean flag = true;

        if(sentence.size() > 0) {
            flag = false;
        }
        return flag;
    }

    @Override
	public Iterator<PartOfSentence> iterator() {
		return sentence.iterator();
	}

	/** This method save general instance of the palidrome list */
    public void setListPalindrome(ListPalindrome listPalindrome) {
        this.listPalindrome = listPalindrome;
    }
}
