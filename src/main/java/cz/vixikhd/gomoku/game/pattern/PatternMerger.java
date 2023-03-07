package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class PatternMerger {
    private static final PatternSymbol FILL_SYMBOL = new PatternSymbol(PatternSymbol.Type.SYMBOL_ANY, -1);

    private final List<String> hashesA = new ArrayList<>();
    private final List<PatternSymbol[][]> partsA = new ArrayList<>();
    private final List<String> hashesB = new ArrayList<>();
    private final List<PatternSymbol[][]> partsB = new ArrayList<>();

    final private Pattern.Type patternType;
    final private String patternName;
    final private String patternDescription;

    final private int priority;

    public PatternMerger(Pattern.Type type, String patternName, String patternDescription, int priority) {
        this.patternType = type;
        this.patternName = patternName;
        this.patternDescription = patternDescription;
        this.priority = priority;
    }
    
    public void addPartA(PatternSymbol[][] symbols, boolean allowTranspose, boolean allowSlope) {
        this.addPart(this.hashesA, this.partsA, symbols, allowTranspose, allowSlope);
    }
    public void addPartB(PatternSymbol[][] symbols, boolean allowTranspose, boolean allowSlope) {
        this.addPart(this.hashesB, this.partsB, symbols, allowTranspose, allowSlope);
    }
    
    private void addPart(final List<String> hashes, final List<PatternSymbol[][]> parts, PatternSymbol[][] symbols, boolean allowTranspose, boolean allowSlope) {
        this.savePartVariation(hashes, parts, symbols);

        if(allowTranspose) {
            PatternSymbol[][] transposed = this.generateTransposedPart(symbols);
            this.savePartVariation(hashes, parts, transposed);

            if(allowSlope) {
                this.savePartVariation(hashes, parts, this.generateSlopePart(transposed));
            }
        }

        if(allowSlope) {
            PatternSymbol[][] slope = this.generateSlopePart(symbols);
            this.savePartVariation(hashes, parts, slope);
        }
    }

    private void savePartVariation(final List<String> hashes, final List<PatternSymbol[][]> parts, PatternSymbol[][] variation) {
        String hash = PatternVariation.symbolHash(variation);
        if(hashes.contains(hash)) {
            return;
        }

        hashes.add(hash);
        parts.add(variation);
    }

    private PatternSymbol[][] generateTransposedPart(PatternSymbol[][] source) {
        return PatternTransform.transpose(source);
    }

    private PatternSymbol[][] generateSlopePart(PatternSymbol[][] original) {
        int offsetX = 0;
        int sizeX = 0, sizeY = 0;

        int tmp;
        for(int y = 0; y < original.length; ++y) {
            for(int x = 0; x < original[y].length; ++x) {
                tmp = x - y;
                if(tmp < 0) {
                    if(tmp < offsetX) {
                        offsetX = tmp;
                    }
                } else {
                    sizeX = Math.max(sizeX, Math.abs(x - y));
                }

                sizeY = Math.max(sizeY, x + y);
            }
        }

        offsetX = -offsetX;
        sizeX += offsetX;

        ++sizeX;
        ++sizeY;

        PatternSymbol[][] slopePattern = new PatternSymbol[sizeY][sizeX];

        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                slopePattern[y][x] = FILL_SYMBOL;
            }
        }

        for(int y = 0; y < original.length; ++y) {
            for(int x = 0; x < original[y].length; ++x) {
                slopePattern[x + y][offsetX + x - y] = original[y][x];
            }
        }

        return slopePattern;
    }

    public List<Pattern> generatePatterns() {
        // Debug
//        int a = 0;
//        for(PatternSymbol[][] symbols : this.parts) {
//            System.out.println("--- == [ Part " + (a++) + " ] == ---");
//
//            for(PatternSymbol[] symbol : symbols) {
//                for(PatternSymbol patternSymbol : symbol) {
//                    System.out.print(patternSymbol.type().getName());
//                }
//                System.out.println();
//            }
//        }

        List<Pattern> generatedPatterns = new ArrayList<>();

        // Iterating over two different pattern parts
        Pattern mergedPattern;
        for(int i = 0, j = this.partsA.size(); i < j; ++i) {
            for(int k = 0, l = this.partsB.size(); k < l; ++k) {
                if(PatternVariation.symbolHash(this.partsA.get(i)).equals(PatternVariation.symbolHash(this.partsB.get(k)))) {
                    continue;
                }

                mergedPattern = this.mergeParts(this.partsA.get(i), this.partsB.get(k), i, k);
                if(mergedPattern != null)
                    generatedPatterns.add(mergedPattern);
            }
        }

        this.hashesA.clear();
        this.partsA.clear();
        this.hashesB.clear();
        this.partsB.clear();

        return generatedPatterns;
    }

    /**
     * Merges two pattern parts into a new pattern
     *
     * @return Returns pattern merged from two parts or null, if the parts could not be merged
     */
    private Pattern mergeParts(PatternSymbol[][] partA, PatternSymbol[][] partB, int a, int b) {
        Vector2i partAPos = null, partBPos = null;

        // Coordinates of the merge symbol after pattern merge
        int mergeX = -1, mergeY = -1;

        // Lookup for merge symbol in partA
        for(int y = 0; y < partA.length; ++y) {
            for(int x = 0; x < partA[y].length; ++x) {
                if(partA[y][x].type().equals(PatternSymbol.Type.MERGE_SYMBOL)) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    partAPos = new Vector2i(x, y);
                }
            }
        }

        // Lookup for merge symbol in partB
        for(int y = 0; y < partB.length; ++y) {
            for(int x = 0; x < partB[y].length; ++x) {
                if(partB[y][x].type().equals(PatternSymbol.Type.MERGE_SYMBOL)) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    partBPos = new Vector2i(x, y);
                }
            }
        }

        assert partAPos != null;
        assert partBPos != null;

        // Dimension of merged pattern
        int sizeX = Math.max(partAPos.getX(), partBPos.getX()) + Math.max(partA[0].length - partAPos.getX(), partB[0].length - partBPos.getX());
        int sizeY = Math.max(partAPos.getY(), partBPos.getY()) + Math.max(partA.length - partAPos.getY(), partB.length - partBPos.getY());

        PatternSymbol[][] merged = new PatternSymbol[sizeY][sizeX];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                merged[y][x] = FILL_SYMBOL;
            }
        }

        Vector2i motion = new Vector2i(mergeX, mergeY).subtractVector(partAPos);

        // Add partA to the merged pattern
        for(int y = 0; y < partA.length; ++y) {
            for(int x = 0; x < partA[y].length; ++x) {
                merged[y + motion.getY()][x + motion.getX()] = partA[y][x];
            }
        }

        motion = new Vector2i(mergeX, mergeY).subtractVector(partBPos);
        for(int y = 0; y < partB.length; ++y) {
            for(int x = 0; x < partB[y].length; ++x) {
                PatternSymbol currentSymbol = merged[y + motion.getY()][x + motion.getX()];
                if(!currentSymbol.type().equals(PatternSymbol.Type.SYMBOL_ANY) && !currentSymbol.type().equals(partB[y][x].type())) {
                    if(partB[y][x].type().equals(PatternSymbol.Type.SYMBOL_ANY)) {
                        continue;
                    }

                    return null;
                }

                merged[y + motion.getY()][x + motion.getX()] = partB[y][x];
            }
        }

        merged[mergeY][mergeX] = new PatternSymbol(PatternSymbol.Type.PLACE_FOR_OUTPLAY, this.priority);

        return new Pattern(this.patternType, this.patternName, this.patternDescription + " " + a + "/" + b, new PatternTransform(merged).generatePatternVariations());
    }
}
