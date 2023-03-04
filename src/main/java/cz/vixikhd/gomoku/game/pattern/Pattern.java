package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.*;

public class Pattern {
    final private String name;
    final private String description;

    final private PatternSymbol[][] pattern;
    final private List<PatternVariation> variations;

    public Pattern(String name, String description, PatternSymbol[][] pattern) {
        try {
            this.validatePattern(pattern);
        } catch (PatternValidationException e) {
            throw new IllegalArgumentException("Got invalid pattern", e);
        }

        this.name = name;
        this.description = description;
        this.pattern = pattern;

        this.variations = new PatternTransform(pattern).generatePatternVariations();
    }

    private void validatePattern(PatternSymbol[][] pattern) throws PatternValidationException {
        if(pattern.length == 0) {
            throw new PatternValidationException("Pattern must have at least one row");
        }

        int expectedLength = -1;
        for(PatternSymbol[] patternSymbols : pattern) {
            if(patternSymbols.length == 0) {
                throw new PatternValidationException("Pattern must have at least one symbol in column");
            }

            if(expectedLength == -1) {
                expectedLength = patternSymbols.length;
                continue;
            }

            if(patternSymbols.length != expectedLength) {
                throw new PatternValidationException("Pattern has not equally sized rows.");
            }
        }
    }

    public PatternSymbol[][] getPattern() {
        return this.pattern;
    }

    public List<PatternVariation> getVariations() {
        return this.variations;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public enum Type {
        OFFENSIVE,
        DEFENSIVE
    }

    public static class PatternVariation {
        final private PatternSymbol[][] symbols;

        private final List<Vector2i> outplayPositionList = new ArrayList<>();

        public PatternVariation(PatternSymbol[][] symbols) {
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
}
