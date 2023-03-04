package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;

import java.util.*;

public class PatternTransform {
    final private PatternSymbol[][] original;
    
    public PatternTransform(final PatternSymbol[][] original) {
        this.original = original;
    }

    /**
     * Generates list of unique pattern variations by transposing and flipping the symbols
     * By transposing and flipping the variation it is also possible to rotate it
     */
    public List<Pattern.PatternVariation> generatePatternVariations() {
        // Hash map creates hash from hash, which means two different hashes may be considered the same
        // And some variations may be lost
        List<String> hashes = new ArrayList<>();
        List<Pattern.PatternVariation> variations = new ArrayList<>();
        
        PatternSymbol[][] tempVariation;
        String tempHash;

        // Base variation
        tempVariation = original.clone();
        hashes.add(this.patternHash(tempVariation));
        variations.add(new Pattern.PatternVariation(tempVariation));
        
        // Flipped variations
        this.generateFlippedVariations(hashes, variations, original);

        // Transposed variation
        PatternSymbol[][] transposed = this.transpose(original);

        tempVariation = transposed;
        tempHash = this.patternHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new Pattern.PatternVariation(tempVariation));
        }

        // Flipped transposed variations
        this.generateFlippedVariations(hashes, variations, transposed);

        return variations;
    }

    private void generateFlippedVariations(List<String> hashes, List<Pattern.PatternVariation> variations, PatternSymbol[][] baseVariation) {
        PatternSymbol[][] tempVariation;
        String tempHash;
        tempVariation = this.flipAroundX(baseVariation);
        tempHash = this.patternHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new Pattern.PatternVariation(tempVariation));
        }
        tempVariation = this.flipAroundY(baseVariation);
        tempHash = this.patternHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new Pattern.PatternVariation(tempVariation));
        }
        tempVariation = this.flipAroundX(tempVariation);
        tempHash = this.patternHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new Pattern.PatternVariation(tempVariation));
        }
    }

    private PatternSymbol[][] transpose(PatternSymbol[][] pattern) {
        int sizeX = pattern[0].length, sizeY = pattern.length;

        PatternSymbol[][] transposedPattern = new PatternSymbol[sizeX][sizeY];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                transposedPattern[x][y] = pattern[y][x];
            }
        }

        return transposedPattern;
    }

    private PatternSymbol[][] flipAroundX(PatternSymbol[][] pattern) {
        PatternSymbol[][] flippedPattern = new PatternSymbol[pattern.length][];
        for(int rowNum = 0; rowNum < pattern.length; ++rowNum) {
            PatternSymbol[] row = pattern[rowNum];
            PatternSymbol[] flippedRow = new PatternSymbol[row.length];

            for(int i = 0, j = row.length - 1; i <= j; ++i, --j) {
                flippedRow[i] = row[j];
                flippedRow[j] = row[i];
            }

            flippedPattern[rowNum] = flippedRow;
        }

        return flippedPattern;
    }

    private PatternSymbol[][] flipAroundY(PatternSymbol[][] pattern) {
        PatternSymbol[][] flippedPattern = new PatternSymbol[pattern.length][];
        for(int i = 0, j = pattern.length - 1; i <= j; ++i, --j) {
            flippedPattern[i] = pattern[j].clone();
            flippedPattern[j] = pattern[i].clone();
        }

        return flippedPattern;
    }

    private String patternHash(PatternSymbol[][] pattern) {
        StringBuilder hash = new StringBuilder();
        for(PatternSymbol[] patternSymbols : pattern) {
            for(PatternSymbol patternSymbol : patternSymbols) {
                hash.append(patternSymbol.type().getName());
            }
            hash.append("//");
        }

        return hash.toString();
    }
}
