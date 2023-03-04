package cz.vixikhd.gomoku.game.pattern;

import java.util.*;

public class PatternTransform {
    final private Pattern.PatternSymbol[][] original;
    
    public PatternTransform(final Pattern.PatternSymbol[][] original) {
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
        
        Pattern.PatternSymbol[][] tempVariation;
        String tempHash;

        // Base variation
        tempVariation = original.clone();
        hashes.add(this.patternHash(tempVariation));
        variations.add(new Pattern.PatternVariation(tempVariation));
        
        // Flipped variations
        this.generateFlippedVariations(hashes, variations, original);

        // Transposed variation
        Pattern.PatternSymbol[][] transposed = this.transpose(original);

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

    private void generateFlippedVariations(List<String> hashes, List<Pattern.PatternVariation> variations, Pattern.PatternSymbol[][] baseVariation) {
        Pattern.PatternSymbol[][] tempVariation;
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

    private Pattern.PatternSymbol[][] transpose(Pattern.PatternSymbol[][] pattern) {
        int sizeX = pattern[0].length, sizeY = pattern.length;

        Pattern.PatternSymbol[][] transposedPattern = new Pattern.PatternSymbol[sizeX][sizeY];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                transposedPattern[x][y] = pattern[y][x];
            }
        }

        return transposedPattern;
    }

    private Pattern.PatternSymbol[][] flipAroundX(Pattern.PatternSymbol[][] pattern) {
        Pattern.PatternSymbol[][] flippedPattern = new Pattern.PatternSymbol[pattern.length][];
        for(int rowNum = 0; rowNum < pattern.length; ++rowNum) {
            Pattern.PatternSymbol[] row = pattern[rowNum];
            Pattern.PatternSymbol[] flippedRow = new Pattern.PatternSymbol[row.length];

            for(int i = 0, j = row.length - 1; i <= j; ++i, --j) {
                flippedRow[i] = row[j];
                flippedRow[j] = row[i];
            }

            flippedPattern[rowNum] = flippedRow;
        }

        return flippedPattern;
    }

    private Pattern.PatternSymbol[][] flipAroundY(Pattern.PatternSymbol[][] pattern) {
        Pattern.PatternSymbol[][] flippedPattern = new Pattern.PatternSymbol[pattern.length][];
        for(int i = 0, j = pattern.length - 1; i <= j; ++i, --j) {
            flippedPattern[i] = pattern[j].clone();
            flippedPattern[j] = pattern[i].clone();
        }

        return flippedPattern;
    }

    private String patternHash(Pattern.PatternSymbol[][] pattern) {
        StringBuilder hash = new StringBuilder();
        for(Pattern.PatternSymbol[] patternSymbols : pattern) {
            for(Pattern.PatternSymbol patternSymbol : patternSymbols) {
                hash.append(patternSymbol.type().getName());
            }
            hash.append("//");
        }

        return hash.toString();
    }
}
