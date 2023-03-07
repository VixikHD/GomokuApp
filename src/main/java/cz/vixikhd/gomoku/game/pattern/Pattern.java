package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;

import java.util.List;

public class Pattern {
    final private Type type;
    final private String name;
    final private String description;

    final private List<PatternVariation> variations;

    final private String hash;

    public Pattern(Pattern.Type type, String name, String description, List<PatternVariation> variations) {
        if(variations.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument given. List of variations must not be empty.");
        }

        this.type = type;
        this.name = name;
        this.description = description;
        this.variations = variations;

        String hash = null, anotherHash;
        for(PatternVariation variation : variations) {
            if(hash == null) {
                hash = PatternVariation.symbolHash(variation.getSymbols());
            } else if(hash.compareTo(anotherHash = PatternVariation.symbolHash(variation.getSymbols())) < 0) {
                hash = anotherHash;
            }
        }

        this.hash = hash;
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

    public List<PatternVariation> getVariations() {
        return this.variations;
    }

    public Pattern.Type getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPatternHash() {
        return this.hash;
    }

    public enum Type {
        OFFENSIVE,
        DEFENSIVE
    }
}
