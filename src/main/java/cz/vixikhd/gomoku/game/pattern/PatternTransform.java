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
    public List<PatternVariation> generatePatternVariations() {
        return this.generatePatternVariations(new ArrayList<>());
    }

    public List<PatternVariation> generatePatternVariations(List<String> excludedHashes) {
        // Hash map creates hash from hash, which means two different hashes may be considered the same
        // And some variations may be lost. That's why HashMap is not used.

        List<PatternVariation> variations = new ArrayList<>();
        
        PatternSymbol[][] tempVariation;
        String tempHash;

        // Base variation
        tempVariation = original.clone();
        tempHash = PatternVariation.symbolHash(tempVariation);
        if(!excludedHashes.contains(tempHash)) {
            excludedHashes.add(PatternVariation.symbolHash(tempVariation));
            variations.add(new PatternVariation(tempVariation));
        }
        
        // Flipped variations
        this.generateFlippedVariations(excludedHashes, variations, original);

        // Transposed variation
        PatternSymbol[][] transposed = PatternTransform.transpose(original);

        tempVariation = transposed;
        tempHash = PatternVariation.symbolHash(tempVariation);
        if(!excludedHashes.contains(tempHash)) {
            excludedHashes.add(tempHash);
            variations.add(new PatternVariation(tempVariation));
        }

        // Flipped transposed variations
        this.generateFlippedVariations(excludedHashes, variations, transposed);

        return variations;
    }

    private void generateFlippedVariations(List<String> hashes, List<PatternVariation> variations, PatternSymbol[][] baseVariation) {
        PatternSymbol[][] tempVariation;
        String tempHash;
        tempVariation = PatternTransform.flipAroundX(baseVariation);
        tempHash = PatternVariation.symbolHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new PatternVariation(tempVariation));
        }
        tempVariation = PatternTransform.flipAroundY(baseVariation);
        tempHash = PatternVariation.symbolHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new PatternVariation(tempVariation));
        }
        tempVariation = PatternTransform.flipAroundX(tempVariation);
        tempHash = PatternVariation.symbolHash(tempVariation);
        if(!hashes.contains(tempHash)) {
            hashes.add(tempHash);
            variations.add(new PatternVariation(tempVariation));
        }
    }

    public static PatternSymbol[][] transpose(final PatternSymbol[][] pattern) {
        int sizeX = pattern[0].length, sizeY = pattern.length;

        PatternSymbol[][] transposedPattern = new PatternSymbol[sizeX][sizeY];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                transposedPattern[x][y] = pattern[y][x];
            }
        }

        return transposedPattern;
    }

    public static PatternSymbol[][] flipAroundX(final PatternSymbol[][] pattern) {
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

    public static PatternSymbol[][] flipAroundY(final PatternSymbol[][] pattern) {
        PatternSymbol[][] flippedPattern = new PatternSymbol[pattern.length][];
        for(int i = 0, j = pattern.length - 1; i <= j; ++i, --j) {
            flippedPattern[i] = pattern[j].clone();
            flippedPattern[j] = pattern[i].clone();
        }

        return flippedPattern;
    }
}
