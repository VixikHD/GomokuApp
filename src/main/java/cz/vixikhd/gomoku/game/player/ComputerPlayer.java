package cz.vixikhd.gomoku.game.player;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.SituatedSymbol;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.game.grid.browser.GridBrowser;
import cz.vixikhd.gomoku.game.grid.pattern.Pattern;
import cz.vixikhd.gomoku.game.grid.pattern.PatternManager;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ComputerPlayer extends Player {
    public ComputerPlayer(Symbol symbol) {
        super(symbol);
    }

    protected Vector2i doLeastMove(GridBrowser browser) {
        GridBrowser.Row[] rows = browser.extractHorizontalRows();

        for(GridBrowser.Row row : rows) {
            for(SituatedSymbol symbol : row.symbols()) {
                if (symbol.symbol().equals(Symbol.NONE)) {
                    return symbol.position();
                }
            }
        }

        return null;
    }

    protected Vector2i doBogoMove(Grid grid) {
        HashMap<Vector2i, Integer> cellsByPriority = new HashMap<>();
        for(int y = 0; y < Grid.GRID_SIZE; ++y) {
            for(int x = 0; x < Grid.GRID_SIZE; ++x) {
                if(!grid.getSymbolAt(x, y).equals(Symbol.NONE)) {
                    continue;
                }

                int priority = 0;

                for(int blockX = -1; blockX <= 1; ++blockX) {
                    for(int blockY = -1; blockY <= 1; ++blockY) {
                        int targetX = x + blockX;
                        int targetY = y + blockY;

                        if(targetX < 0 || targetX >= Grid.GRID_SIZE || targetY < 0 || targetY >= Grid.GRID_SIZE) {
                            continue;
                        }

                        if(!grid.getSymbolAt(targetX, targetY).equals(Symbol.NONE)) {
                            ++priority;
                        }
                    }
                }

                if(priority > 0) {
                    cellsByPriority.put(new Vector2i(x, y), priority);
                }
            }
        }

        if(cellsByPriority.isEmpty()) {
            return null;
        }

        int maxPriority = -1;
        Vector2i finalPosition = null;
        for(Vector2i pos : cellsByPriority.keySet()) {
            if(finalPosition == null) {
                finalPosition = pos;
                maxPriority = cellsByPriority.get(pos);
                continue;
            }

            if(cellsByPriority.get(pos) > maxPriority) {
                finalPosition = pos;
                maxPriority = cellsByPriority.get(pos);
            }
        }

        return finalPosition;
    }

    protected Vector2i doDefensivePatternMove(Grid grid, List<Pattern> patternCollection) {
        for(Pattern pattern : patternCollection) {
            List<Grid.MatchedPattern> matchedPatterns = grid.matchPattern(this.symbol, pattern);

            if(matchedPatterns.isEmpty()) {
                continue;
            }

            Grid.MatchedPattern randomMatchedPattern = matchedPatterns.get(Game.getRandom().nextInt(matchedPatterns.size()));

            Pattern.PatternVariation variation = randomMatchedPattern.variation();
            Vector2i randomOutplayPosition = variation.getOutplayPositionList().get(Game.getRandom().nextInt(variation.getOutplayPositionList().size()));

            System.out.println("Matched defensive pattern from " + randomMatchedPattern.start() + " to " + randomMatchedPattern.end() + "outplay position " + randomMatchedPattern.start().addVector(randomOutplayPosition));

            return randomMatchedPattern.start().addVector(randomOutplayPosition);
        }

        return null;
    }

    protected Vector2i doOffensivePatternMove(Grid grid, List<Pattern> patternCollection) {
        for(Pattern pattern : patternCollection) {
            List<Grid.MatchedPattern> matchedPatterns = grid.matchPattern(this.symbol, pattern);

            if(matchedPatterns.isEmpty()) {
                continue;
            }

            Grid.MatchedPattern randomMatchedPattern = matchedPatterns.get(Game.getRandom().nextInt(matchedPatterns.size()));

            Pattern.PatternVariation variation = randomMatchedPattern.variation();
            Vector2i randomOutplayPosition = variation.getOutplayPositionList().get(Game.getRandom().nextInt(variation.getOutplayPositionList().size()));

            System.out.println("Matched offensive pattern from " + randomMatchedPattern.start() + " to " + randomMatchedPattern.end() + "outplay position " + randomMatchedPattern.start().addVector(randomOutplayPosition));

            return randomMatchedPattern.start().addVector(randomOutplayPosition);
        }

        return null;
    }

    protected Vector2i doMove(Grid grid) {
        if(grid.countSymbols(null) == 0) {
            return grid.center();
        }

        Vector2i position;
        if((position = this.doDefensivePatternMove(grid, PatternManager.getDefensivePatterns())) != null) {
            return position;
        }

        if((position = this.doOffensivePatternMove(grid, PatternManager.getOffensivePatterns())) != null) {
            return position;
        }

        if((position = this.doBogoMove(grid)) != null) {
            return position;
        }

        return this.doLeastMove(grid.getBrowser());
    }

    @Override
    public void requestMove(Game game, Function<Vector2i, Boolean> targetPosRequestClosure) {
        Vector2i target = this.doMove(game.getGrid());
        if(target == null) {
            throw new RuntimeException("Could not find move for the grid.");
        }

        if(!targetPosRequestClosure.apply(target)) {
            throw new RuntimeException("Requested move for full grid.");
        }
    }
}
