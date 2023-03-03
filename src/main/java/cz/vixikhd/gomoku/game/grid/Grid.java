package cz.vixikhd.gomoku.game.grid;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.grid.browser.GridBrowser;
import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.*;
import java.util.stream.Collectors;

public class Grid implements GridInterface {
    final public static int GRID_SIZE = 15;

    final private Symbol[][] grid = new Symbol[GRID_SIZE][GRID_SIZE];

    final private GridBrowser browser;

    private Grid() {
        this.browser = new GridBrowser(this);
    }

    private List<Integer> matchPatternRow(Symbol player, Symbol[] row, Pattern.PatternSymbol[] pattern) {
        List<Integer> matches = new ArrayList<>();
        for(int x = 0; x <= row.length - pattern.length; ++x) {
            boolean found = true;
            for(int i = 0; i < pattern.length; ++i) {
                if(!pattern[i].type().accepts(row[x + i], player)) {
                    found = false;
                    break;
                }
            }

            if(found) {
                matches.add(x);
            }
        }
        return matches;
    }

    public List<Grid.MatchedPattern> matchPattern(Symbol player, Pattern pattern) {
        List<Grid.MatchedPattern> matchedVariations = new ArrayList<>();

        for(Pattern.PatternVariation variation : pattern.getVariations()) {
            Pattern.PatternSymbol[][] symbols = variation.getSymbols();
            int sizeX = symbols[0].length, sizeY = symbols.length;

            for(int possibleStartY = 0; possibleStartY <= Grid.GRID_SIZE - sizeY; ++possibleStartY) {
                Map<Integer, List<Integer>> matchesInRow = new HashMap<>();
                for(int patternRow = 0; patternRow < sizeY; ++patternRow) {
                    matchesInRow.put(patternRow, this.matchPatternRow(player, this.grid[possibleStartY + patternRow], symbols[patternRow]));
                }

                // 0 1 6
                // 6 7
                // 0 2 6

                List<Integer> variationMatches = matchesInRow.get(0);
                for(int patternRow = 1; patternRow < matchesInRow.size(); ++patternRow) {
                    int finalPatternRow = patternRow;
                    variationMatches = variationMatches.stream().filter(startX -> matchesInRow.get(finalPatternRow).contains(startX)).collect(Collectors.toList());

                    if(variationMatches.size() == 0) {
                        break;
                    }
                }

                if(variationMatches.size() == 0) {
                    continue;
                }

                for(Integer x : variationMatches) {
                    matchedVariations.add(new MatchedPattern(new Vector2i(x, possibleStartY), new Vector2i(sizeX + x - 1, sizeY + possibleStartY - 1), variation));
                }
            }
        }

        return matchedVariations;
    }

    public int countSymbols(Symbol symbol) {
        int count = 0;
        for(int y = 0; y < GRID_SIZE; ++y) {
            for(int x = 0; x < GRID_SIZE; ++x) {
                if((symbol == null && !this.grid[y][x].equals(Symbol.NONE)) || this.grid[y][x].equals(symbol)) {
                    ++count;
                }
            }
        }
        return count;
    }

    public Vector2i center() {
        return new Vector2i((GRID_SIZE - 1) / 2, (GRID_SIZE - 1) / 2);
    }

    public Symbol getSymbolAt(int x, int y) {
        this.assertCoordsInsideGrid(x, y);

        return this.grid[y][x];
    }

    public Symbol getSymbol(Vector2i coords) {
        return this.getSymbolAt(coords.getX(), coords.getY());
    }

    public void updateSymbolAt(int x, int y, Symbol symbol) {
        this.assertCoordsInsideGrid(x, y);

        this.grid[y][x] = symbol;
    }

    public void updateSymbol(Vector2i coords, Symbol symbol) {
        this.updateSymbolAt(coords.getX(), coords.getY(), symbol);
    }

    protected void assertCoordsInsideGrid(int x, int y) {
        if(x < 0 || x >= Grid.GRID_SIZE) {
            throw new RuntimeException("Invalid x grid coordinate given");
        }
        if(y < 0 || y >= Grid.GRID_SIZE) {
            throw new RuntimeException("Invalid y grid coordinate given");
        }
    }

    public GridBrowser getBrowser() {
        return browser;
    }

    public static Grid emptyGrid() {
        Grid grid = new Grid();
        for(int y = 0; y < Grid.GRID_SIZE; ++y) {
            for(int x = 0; x < Grid.GRID_SIZE; ++x) {
                grid.grid[y][x] = Symbol.NONE;
            }
        }

        return grid;
    }

    public record MatchedPattern(Vector2i start, Vector2i end, Pattern.PatternVariation variation) {}
}
