package cz.vixikhd.gomoku.game.pattern.symbol;

import cz.vixikhd.gomoku.game.Symbol;

import java.util.Arrays;
import java.util.List;

public record PatternSymbol(PatternSymbol.Type type, int priority) {
    public enum Type {
        SYMBOL_PLAYER(0, 'P', new Symbol[] {Symbol.X}, new Symbol[] {Symbol.O}),
        SYMBOL_OPPONENT(1, 'O', new Symbol[] {Symbol.O}, new Symbol[] {Symbol.X}),
        SYMBOL_NONE(2, 'N', new Symbol[] {Symbol.NONE}, new Symbol[] {Symbol.NONE}),
        SYMBOL_ANY(3, 'A', new Symbol[] {Symbol.X, Symbol.O, Symbol.NONE, Symbol.BORDER}, new Symbol[] {Symbol.X, Symbol.O, Symbol.NONE, Symbol.BORDER}),
        SYMBOL_PLAYER_NONE(4, 'X', new Symbol[] {Symbol.X, Symbol.NONE}, new Symbol[] {Symbol.O, Symbol.NONE}),
        SYMBOL_OPPONENT_NONE(5, 'Y', new Symbol[] {Symbol.O, Symbol.NONE}, new Symbol[] {Symbol.X, Symbol.NONE}),
        PLACE_FOR_OUTPLAY(6, 'R', new Symbol[] {Symbol.NONE}, new Symbol[] {Symbol.NONE});

        final private int id;
        final private char name;

        final private List<Symbol> acceptedSymbolsForX;
        final private List<Symbol> acceptedSymbolsForO;

        Type(int id, char name, Symbol[] acceptedSymbolsForX, Symbol[] acceptedSymbolsForO) {
            this.id = id;
            this.name = name;

            this.acceptedSymbolsForX = Arrays.asList(acceptedSymbolsForX);
            this.acceptedSymbolsForO = Arrays.asList(acceptedSymbolsForO);
        }

        public static Type parsePatternSymbol(char name) {
            if(name >= '0' && name <= '9') {
                return Type.PLACE_FOR_OUTPLAY;
//                return Type.PLACE_FOR_OUTPLAY.setPriority(name - '0');
            }

            for(Type symbol : Type.class.getEnumConstants()) {
                if(symbol.getName() == name) {
                    return symbol;
                }
            }

            throw new IllegalArgumentException("Invalid symbol name given");
        }

        public boolean accepts(Symbol symbol, Symbol player) {
            if(player.equals(Symbol.X)) {
                return this.acceptedSymbolsForX.contains(symbol);
            } else if (player.equals(Symbol.O)) {
                return this.acceptedSymbolsForO.contains(symbol);
            }

            throw new IllegalArgumentException("Player could not play with symbol '" + player.getTextValue() + "'");
        }

        public int getId() {
            return this.id;
        }

        public char getName() {
            return this.name;
        }
    }
}

