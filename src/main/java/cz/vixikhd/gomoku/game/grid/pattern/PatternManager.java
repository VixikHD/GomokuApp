package cz.vixikhd.gomoku.game.grid.pattern;

import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class PatternManager {
    final private static List<Pattern> defensivePatterns = new ArrayList<>();
    final private static List<Pattern> offensivePatterns = new ArrayList<>();

    public static void init() {
        if(!PatternManager.defensivePatterns.isEmpty()) {
            return;
        }

        PatternManager.loadDefensivePatterns();
        PatternManager.loadOffensivePatterns();
    }
    
    private static void loadDefensivePatterns() {
        // Four in row, partially blocked
        defensivePatterns.add(new Pattern("Closed Four In Row", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'O', 'O', 'O', 'O', '0'}
        })));
        defensivePatterns.add(new Pattern("Diagonal Closed Four In Row", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'O', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'O', 'A', 'A', 'A'},
            {'A', 'A', 'A', 'O', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'A', '0'}
        })));

        // Three plus one
        defensivePatterns.add(new Pattern("Three Plus One", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'O', 'O', 'O', '0' , 'O'}
        })));
        defensivePatterns.add(new Pattern("Diagonal Three Plus One", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'O', 'A', 'A', 'A', 'A'},
            {'A', 'O', 'A', 'A', 'A'},
            {'A', 'A', 'O', 'A', 'A'},
            {'A', 'A', 'A', '0', 'A'},
            {'A', 'A', 'A', 'A', 'O'}
        })));

        // Two plus two
        defensivePatterns.add(new Pattern("Three Plus One", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'O', 'O', '0', 'O', 'O'}
        })));
        defensivePatterns.add(new Pattern("Diagonal Three Plus One", "Requires immediate block, expect the situation you already have half-open four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'O', 'A', 'A', 'A', 'A'},
            {'A', 'O', 'A', 'A', 'A'},
            {'A', 'A', '0', 'A', 'A'},
            {'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'O'}
        })));

        // Three in row
        defensivePatterns.add(new Pattern("Open Three In Row", "Three in row requires block in the case you haven't opened three in the row already.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'1', 'O', 'O', 'O', '1'}
        })));
        // Three in row diagonally
        defensivePatterns.add(new Pattern("Diagonal Open Three In Row", "Three in row requires block in the case you haven't opened three in the row already.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'1', 'A', 'A', 'A', 'A'},
            {'A', 'O', 'A', 'A', 'A'},
            {'A', 'A', 'O', 'A', 'A'},
            {'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', '1'}
        })));

        // Two plus one
        defensivePatterns.add(new Pattern("Two Plus One", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'O', 'O', '1', 'O', 'N'}
        })));
        defensivePatterns.add(new Pattern("Diagonal Two Plus One", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'O', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'O', 'A', 'A', 'A'},
            {'A', 'A', 'A', '1', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'A', 'N'},
        })));

        // ----------------------
        // Merged Patterns
        // ----------------------

        char[][][] parts = new char[][][] {
            {
                {'N'},
                {'$'},
                {'O'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'$'},
                {'N'},
                {'O'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'$'},
                {'O'},
                {'N'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'O'},
                {'$'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'O'},
                {'$'},
                {'N'},
                {'O'},
                {'N'}
            },
            {
                {'N', '$', 'O', 'O', 'N'},
            },
            {
                {'N', '$', 'N', 'O', 'O', 'N'},
            },
            {
                {'N', '$', 'O', 'N', 'O', 'N'},
            },
            {
                {'N', 'O', '$', 'O', 'N'},
            },
            {
                {'N', 'O', '$', 'N', 'O', 'N'},
            },
            {
                {'N', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'A', 'A', '$', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'A', 'A', '$', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'O', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', '$', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', '$', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
        };

        int n = 0;
        for(int i = 0, j = parts.length - 1; i < j; ++i) {
            for(int k = i + 1; k < parts.length; ++k) {
                char[][] merged = PatternManager.mergePatterns(parts[i], parts[k], '$', '2');
                if(merged == null) {
                    continue;
                }

                defensivePatterns.add(new Pattern("Two And Two", ++n + ". Variation", Pattern.PatternSymbol.parsePatternSymbolGrid(merged)));
            }
        }
    }

    private static char[][] mergePatterns(char[][] pattern1, char[][] pattern2, char mergeSymbol, char mergeSymbolReplacement) {
        Vector2i pattern1Pos = null, pattern2Pos = null;
        int mergeX = -1, mergeY = -1;

        for(int y = 0; y < pattern1.length; ++y) {
            for(int x = 0; x < pattern1[y].length; ++x) {
                if(pattern1[y][x] == mergeSymbol) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    pattern1Pos = new Vector2i(x, y);
                }
            }
        }
        
        for(int y = 0; y < pattern2.length; ++y) {
            for(int x = 0; x < pattern2[y].length; ++x) {
                if(pattern2[y][x] == mergeSymbol) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    pattern2Pos = new Vector2i(x, y);
                }
            }
        }

        assert pattern1Pos != null;
        assert pattern2Pos != null;

        int sizeX = Math.max(pattern1Pos.getX(), pattern2Pos.getX()) + Math.max(pattern1[0].length - pattern1Pos.getX(), pattern2[0].length - pattern2Pos.getX());
        int sizeY = Math.max(pattern1Pos.getY(), pattern2Pos.getY()) + Math.max(pattern1.length - pattern1Pos.getY(), pattern2.length - pattern2Pos.getY());

        char[][] merged = new char[sizeY][sizeX];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                merged[y][x] = 'A';
            }
        }

        Vector2i motion = new Vector2i(mergeX, mergeY).subtractVector(pattern1Pos);

        for(int y = 0; y < pattern1.length; ++y) {
            for(int x = 0; x < pattern1[y].length; ++x) {
                merged[y + motion.getY()][x + motion.getX()] = pattern1[y][x];
            }
        }

        motion = new Vector2i(mergeX, mergeY).subtractVector(pattern2Pos);
        for(int y = 0; y < pattern2.length; ++y) {
            for(int x = 0; x < pattern2[y].length; ++x) {
                char currentSymbol = merged[y + motion.getY()][x + motion.getX()];
                if(currentSymbol != 'A' && currentSymbol != pattern2[y][x]) {
                    if(pattern2[y][x] == 'A') {
                        continue;
                    }

                    return null;
                }

                merged[y + motion.getY()][x + motion.getX()] = pattern2[y][x];
            }
        }

        merged[mergeY][mergeX] = mergeSymbolReplacement;

        return merged;
    }

    private static void loadOffensivePatterns() {
        // Four in row -> Five
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', 'P', 'P', '0'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', '0'}
        })));
        // Three plus one
        offensivePatterns.add(new Pattern("Three plus one", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', 'P', '0', 'P'}
        })));
        offensivePatterns.add(new Pattern("Three plus one", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', '0', 'A'},
            {'A', 'A', 'A', 'A', 'P'}
        })));
        // Two plus two
        offensivePatterns.add(new Pattern("Two plus two", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', '0', 'P', 'P'}
        })));
        offensivePatterns.add(new Pattern("Diagonal two plus two", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', '0', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', 'P'}
        })));

        // Open three -> Four
        offensivePatterns.add(new Pattern("Open three in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', '1', 'P', 'P', 'P', '1', 'N'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'A', 'A', 'A', 'A', 'A', 'A'},
            {'A', '1', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'A', '1', 'A'},
            {'A', 'A', 'A', 'A', 'A', 'A', 'N'}
        })));

        offensivePatterns.add(new Pattern("Open three in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'P', 'P', '1', 'P', 'N'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'A', '1', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', 'A', 'N'}
        })));
    }

    public static List<Pattern> getOffensivePatterns() {
        return PatternManager.offensivePatterns;
    }

    public static List<Pattern> getDefensivePatterns() {
        return PatternManager.defensivePatterns;
    }
}
