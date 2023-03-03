package cz.vixikhd.gomoku.game.pattern.symbol;

import cz.vixikhd.gomoku.game.pattern.Pattern;

public class SymbolParser {
    public static Pattern.PatternSymbol[][] parseSymbolArray(String[] symbolArray) throws PatternParseException {
        Pattern.PatternSymbol[][] symbols = new Pattern.PatternSymbol[symbolArray.length][];

        int rowSize = -1;
        for(int y = 0; y < symbolArray.length; ++y) {
            if(rowSize == -1) {
                rowSize = symbolArray[y].length();
            } else if(rowSize != symbolArray[y].length())
                throw new PatternParseException((y + 1) + ". row size does not match 1. row size");

            symbols[y] = new Pattern.PatternSymbol[rowSize];
            for(int x = 0; x < rowSize; ++x) {
                symbols[y][x] = SymbolParser.parseSymbol(symbolArray[y].charAt(x));
            }
        }

        return symbols;
    }

    public static Pattern.PatternSymbol parseSymbol(char symbol) throws PatternParseException {
        try {
            int priority = -1;
            if(symbol >= '0' && symbol <= '9') {
                priority = symbol - '0';
            }

            return new Pattern.PatternSymbol(Pattern.PatternSymbolType.parsePatternSymbol(symbol), priority);
        } catch (IllegalArgumentException e) {
            throw new PatternParseException(e.getMessage());
        }
    }
}
