package by.epam.task2.component;

/**
 * This class represents the character at the end of the word
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public abstract class Symbol {
    private char symbol;

    public Symbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
