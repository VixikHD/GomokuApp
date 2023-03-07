package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class PatternVariation {
    final private PatternSymbol[][] symbols;

    private final List<Vector2i> outplayPositionList = new ArrayList<>();

    public PatternVariation(final PatternSymbol[][] symbols) {
        this.symbols = symbols;

        this.calculateOutplayPositionList();
    }

    private void calculateOutplayPositionList() {
        for(int y = 0; y < this.symbols.length; ++y) {
            for(int x = 0; x < this.symbols[y].length; ++x) {
                if(this.symbols[y][x].type().equals(PatternSymbol.Type.PLACE_FOR_OUTPLAY))
                    this.outplayPositionList.add(new Vector2i(x, y));
            }
        }
    }

    public PatternSymbol getSymbol(Vector2i position) {
        return this.getSymbolAt(position.getX(), position.getY());
    }

    public PatternSymbol getSymbolAt(int x, int y) {
        if(!this.validateSymbolPosition(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of bounds");
        }

        return this.symbols[y][x];
    }

    public static String symbolHash(PatternSymbol[][] symbols) {
        StringBuilder hash = new StringBuilder();
        for(PatternSymbol[] patternSymbols : symbols) {
            for(PatternSymbol patternSymbol : patternSymbols) {
                hash.append(patternSymbol.type().getName());
            }
            hash.append("/");
        }

        return hash.toString();
    }

    private boolean validateSymbolPosition(int x, int y) {
        return y >= 0 && y < this.symbols.length && x >= 0 && x < this.symbols[y].length;
    }

    public PatternSymbol[][] getSymbols() {
        return this.symbols;
    }

    public List<Vector2i> getOutplayPositionList() {
        return this.outplayPositionList;
    }
}
