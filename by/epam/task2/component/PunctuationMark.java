package by.epam.task2.component;

import by.epam.task2.constants.Constants;
import by.epam.task2.interfaces.PartOfSentence;

/**
 * This class represents a punctuation mark, provides methods to check
 * whether the symbol is a punctuation mark and whether it marks the end of
 * sentence.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class PunctuationMark extends Symbol implements PartOfSentence {
    private boolean endSentence = false;

    /** Creates a symbol and indicates the status of the offer: completed or not */
    private PunctuationMark(char symbol) {
        super(symbol);
        if(symbol == '?' || symbol == '!' || symbol == '.') endSentence = true;
    }

    /** Method checks if the last character is a letter */
    private static boolean isPunctuationMark(char symbol){
        boolean flag = true;
        String stringSymbol = String.valueOf(symbol);

        if(stringSymbol.matches(Constants.REGEX_SEARCH_LETTERS)){
            flag = false;
        }
        return flag;
    }

    /** Method creates object PunctuationMark if last character punctuation mark*/
    public static PunctuationMark createPunctuationMark (char symbol){
        PunctuationMark punctuationMark = null;

        if (isPunctuationMark(symbol)) {
            punctuationMark = new PunctuationMark(symbol);
        }
        return punctuationMark;
    }

    /** Method indicates end of sentence */
    protected boolean isEndSentence(){
        return endSentence;
    }
}
