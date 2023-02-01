package cz.vixikhd.gomoku.game.grid.pattern;

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

        // Two And Two (H + V)
        defensivePatterns.add(new Pattern("Two And Two (1. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'O', '2', 'N'},
            {'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'N', 'A'},
        })));
        defensivePatterns.add(new Pattern("Two And Two (2. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'O', 'N', '2', 'N'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
        })));
        defensivePatterns.add(new Pattern("Two And Two (3. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'O', 'N', '2', 'N'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
        })));
        defensivePatterns.add(new Pattern("Two And Two (3. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'N', 'O', '2', 'N'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
        })));

        defensivePatterns.add(new Pattern("Two And Two (3. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'N', 'O', '2', 'N'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
        })));
        defensivePatterns.add(new Pattern("Two And Two (3. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'N', 'O', 'N', 'O', '2', 'N'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
            {'A', 'A', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'A', 'N', 'A'},
        })));

        // Two and Two (D + V)
        defensivePatterns.add(new Pattern("Two And Two (4. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'O', 'O', 'A'},
            {'A', 'O', 'A', 'O', 'A'},
            {'N', 'A', 'A', 'N', 'A'},
        })));
        defensivePatterns.add(new Pattern("Two And Two (5. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'O', 'N', 'A'},
            {'A', 'O', 'A', 'O', 'A'},
            {'N', 'A', 'A', 'O', 'A'},
            {'A', 'A', 'A', 'N', 'A'}
        })));
        defensivePatterns.add(new Pattern("Two And Two (6. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'A', 'N', 'O', 'A'},
            {'A', 'A', 'O', 'A', 'O', 'A'},
            {'A', 'O', 'A', 'A', 'N', 'A'},
            {'N', 'A', 'A', 'A', 'A', 'A'}
        })));
        defensivePatterns.add(new Pattern("Two And Two (7. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'A', 'N', 'N', 'A'},
            {'A', 'A', 'O', 'A', 'O', 'A'},
            {'A', 'O', 'A', 'A', 'O', 'A'},
            {'N', 'A', 'A', 'A', 'N', 'A'}
        })));
        defensivePatterns.add(new Pattern("Two And Two (8. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'A', 'O', 'N', 'A'},
            {'A', 'A', 'N', 'A', 'O', 'A'},
            {'A', 'O', 'A', 'A', 'O', 'A'},
            {'N', 'A', 'A', 'A', 'N', 'A'}
        })));
        defensivePatterns.add(new Pattern("Two And Two (9. Variation)", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'A', 'A', 'A', 'N', 'N'},
            {'A', 'A', 'A', 'A', '2', 'A'},
            {'A', 'A', 'A', 'N', 'O', 'A'},
            {'A', 'A', 'O', 'A', 'N', 'A'},
            {'A', 'O', 'A', 'A', 'O', 'A'},
            {'N', 'A', 'A', 'A', 'N', 'A'}
        })));

        // 3 + 3
        defensivePatterns.add(new Pattern("Three And Three", "Should be blocked", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'A', 'O', 'O', 'O', 'N', '2', 'N', 'O', 'O', 'O', 'A'}
        })));
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
