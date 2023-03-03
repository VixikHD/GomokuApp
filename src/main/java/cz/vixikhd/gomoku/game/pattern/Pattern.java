package cz.vixikhd.gomoku.game.pattern;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.graphic.Board;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class Pattern {
    final private String name;
    final private String description;

    final private PatternSymbol[][] pattern;

    final private List<PatternVariation> variations = new ArrayList<>();

    public Pattern(String name, String description, PatternSymbol[][] pattern) {
        try {
            this.validatePattern(pattern);
        } catch (PatternValidationException e) {
            throw new IllegalArgumentException("Got invalid pattern", e);
        }

        this.name = name;
        this.description = description;
        this.pattern = pattern;

        // Base variation
        this.variations.add(this.createPatternVariation(pattern.clone()));
        // Flipped variations
        this.variations.add(this.createPatternVariation(this.flipAroundX(pattern)));
        this.variations.add(this.createPatternVariation(this.flipAroundY(pattern)));
        this.variations.add(this.createPatternVariation(this.flipAroundX(this.flipAroundY(pattern))));

        // Transposed variation
        PatternSymbol[][] transposed = this.transpose(pattern);
        this.variations.add(this.createPatternVariation(transposed));
        // Flipped transposed variations
        this.variations.add(this.createPatternVariation(this.flipAroundX(transposed)));
        this.variations.add(this.createPatternVariation(this.flipAroundY(transposed)));
        this.variations.add(this.createPatternVariation(this.flipAroundX(this.flipAroundY(transposed))));

        // Removing duplicates
        this.removeDuplicateVariations();

//        this.debugDisplayVariations();
    }

    private PatternVariation createPatternVariation(PatternSymbol[][] symbols) {
        return new PatternVariation(this.patternHash(symbols), symbols);
    }

    private void debugDisplayVariations() {
        for(PatternVariation variation : this.variations) {
            Platform.runLater(() -> this.displayVariation(variation.getSymbols()));
        }
    }

    private void displayVariation(PatternSymbol[][] variation) {
        Stage stage  = new Stage();
        stage.setTitle("Debug");

        Board board = new Board(new Vector2i(variation[0].length, variation.length), 30, 5, null);
        for(int y = 0; y < variation.length; ++y) {
            for(int x = 0; x < variation[y].length; ++x) {
                if(variation[y][x].type().equals(PatternSymbolType.SYMBOL_PLAYER)) {
                    board.setSymbolAt(x, y, Symbol.O);
                } else if(variation[y][x].type().equals(PatternSymbolType.SYMBOL_OPPONENT)) {
                    board.setSymbolAt(x, y, Symbol.X);
                }
            }
        }

        stage.setScene(new Scene(board, 200, 200));
        stage.show();
    }

    private void removeDuplicateVariations() {
        Set<String> hashes = new HashSet<>();

        List<PatternVariation> cleaned = new ArrayList<>();
        for(PatternVariation variation : this.variations) {
            if(hashes.contains(variation.getHash())) {
                continue;
            }

            cleaned.add(variation);
            hashes.add(variation.getHash());
        }

        this.variations.clear();
        this.variations.addAll(cleaned);
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

    public record PatternSymbol(PatternSymbolType type, int priority) {
        /**
         * Placeholders:
         * <p></p>
         * Player => P
         * Opponent => O
         * <p></p>
         * None => N
         * Any => A
         * Not-Opponent => X
         * <p></p>
         * Place For Outplay => R
         */
        public static PatternSymbol[][] parsePatternSymbolGrid(char[][] grid) {
            PatternSymbol[][] symbolGrid = new PatternSymbol[grid.length][];
            for(int y = 0; y < grid.length; ++y) {
                symbolGrid[y] = new PatternSymbol[grid[y].length];
                for(int x = 0; x < grid[y].length; ++x) {
                    int priority = -1;
                    if(grid[y][x] >= '0' && grid[y][x] <= '9') {
                        priority = grid[y][x] - '0';
                    }
                    
                    symbolGrid[y][x] = new PatternSymbol(PatternSymbolType.parsePatternSymbol(grid[y][x]), priority);
                }
            }
            return symbolGrid;
        }
    }
    
    public enum PatternSymbolType {
        SYMBOL_PLAYER(0, 'P', new Symbol[] {Symbol.X}, new Symbol[] {Symbol.O}),
        SYMBOL_OPPONENT(1, 'O', new Symbol[] {Symbol.O}, new Symbol[] {Symbol.X}),
        SYMBOL_NONE(2, 'N', new Symbol[] {Symbol.NONE}, new Symbol[] {Symbol.NONE}),
        SYMBOL_ANY(3, 'A', new Symbol[] {Symbol.X, Symbol.O, Symbol.NONE, Symbol.BORDER}, new Symbol[] {Symbol.X, Symbol.O, Symbol.NONE, Symbol.BORDER}),
        SYMBOL_PLAYER_NONE(4, 'X', new Symbol[] {Symbol.X, Symbol.NONE}, new Symbol[] {Symbol.O, Symbol.NONE}),
        SYMBOL_OPPONENT_NONE(5, 'Y', new Symbol[] {Symbol.O, Symbol.NONE}, new Symbol[] {Symbol.X, Symbol.NONE}),
        PLACE_FOR_OUTPLAY(6, 'R', new Symbol[] {Symbol.NONE}, new Symbol[] {Symbol.NONE});

        final private int id;
        final private char name;

        final private List<Symbol> acceptedSymbolsForX;
        final private List<Symbol> acceptedSymbolsForO;

        PatternSymbolType(int id, char name, Symbol[] acceptedSymbolsForX, Symbol[] acceptedSymbolsForO) {
            this.id = id;
            this.name = name;
            
            this.acceptedSymbolsForX = Arrays.asList(acceptedSymbolsForX);
            this.acceptedSymbolsForO = Arrays.asList(acceptedSymbolsForO);
        }

        public static PatternSymbolType parsePatternSymbol(char name) {
            if(name >= '0' && name <= '9') {
                return PatternSymbolType.PLACE_FOR_OUTPLAY;
//                return PatternSymbolType.PLACE_FOR_OUTPLAY.setPriority(name - '0');
            }
            
            for(PatternSymbolType symbol : PatternSymbolType.class.getEnumConstants()) {
                if(symbol.getName() == name) {
                    return symbol;
                }
            }

            throw new IllegalArgumentException("Invalid symbol name given");
        }

        public boolean accepts(Symbol symbol, Symbol player) {
            if(player.equals(Symbol.X)) {
                return this.acceptedSymbolsForX.contains(symbol);
            } else if (player.equals(Symbol.O)) {
                return this.acceptedSymbolsForO.contains(symbol);
            }

            throw new IllegalArgumentException("Player could not play with symbol '" + player.getTextValue() + "'");
        }

        public int getId() {
            return this.id;
        }

        public char getName() {
            return this.name;
        }
    }

    public static class PatternVariation {
        final private String hash;
        final private PatternSymbol[][] symbols;

        private final List<Vector2i> outplayPositionList = new ArrayList<>();

        public PatternVariation(String hash, PatternSymbol[][] symbols) {
            this.hash = hash;
            this.symbols = symbols;

            this.calculateOutplayPositionList();
        }

        private void calculateOutplayPositionList() {
            for(int y = 0; y < this.symbols.length; ++y) {
                for(int x = 0; x < this.symbols[y].length; ++x) {
                    if(this.symbols[y][x].type().equals(PatternSymbolType.PLACE_FOR_OUTPLAY))
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

        public String getHash() {
            return this.hash;
        }

        public PatternSymbol[][] getSymbols() {
            return this.symbols;
        }

        public List<Vector2i> getOutplayPositionList() {
            return this.outplayPositionList;
        }
    }
}
