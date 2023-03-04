package cz.vixikhd.gomoku.game.pattern.symbol;

public class SymbolParser {
    public static PatternSymbol[][] parseSymbolArray(String[] symbolArray) throws PatternParseException {
        PatternSymbol[][] symbols = new PatternSymbol[symbolArray.length][];

        int rowSize = -1;
        for(int y = 0; y < symbolArray.length; ++y) {
            if(rowSize == -1) {
                rowSize = symbolArray[y].length();
            } else if(rowSize != symbolArray[y].length())
                throw new PatternParseException((y + 1) + ". row size does not match 1. row size");

            symbols[y] = new PatternSymbol[rowSize];
            for(int x = 0; x < rowSize; ++x) {
                symbols[y][x] = SymbolParser.parseSymbol(symbolArray[y].charAt(x));
            }
        }

        return symbols;
    }

    public static PatternSymbol parseSymbol(char symbol) throws PatternParseException {
        try {
            int priority = -1;
            if(symbol >= '0' && symbol <= '9') {
                priority = symbol - '0';
            }

            return new PatternSymbol(PatternSymbol.Type.parsePatternSymbol(symbol), priority);
        } catch (IllegalArgumentException e) {
            throw new PatternParseException(e.getMessage());
        }
    }
}
