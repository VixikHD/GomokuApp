package cz.vixikhd.gomoku.game.grid.browser;

import cz.vixikhd.gomoku.game.SituatedSymbol;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.stream.Stream;

/**
 * Allows to browse the grid row-by-row in all directions
 */
public class GridBrowser {
    private final Grid grid;

    public GridBrowser(final Grid grid) {
        this.grid = grid;
    }

    public Row[] extractAllRows() {
        return Stream.of(
            this.extractHorizontalRows(),
            this.extractVerticalRows(),
            this.extractDiagonalHorizontalRows(),
            this.extractDiagonalVerticalRows()
        ).flatMap(Stream::of).toArray(Row[]::new);
    }

    public Row[] extractHorizontalRows() {
        Row[] rows = new Row[Grid.GRID_SIZE];
        for(int y = 0; y < Grid.GRID_SIZE; ++y) {
            SituatedSymbol[] symbols = new SituatedSymbol[Grid.GRID_SIZE];
            for(int x = 0; x < Grid.GRID_SIZE; ++x) {
                symbols[x] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }

            rows[y] = new Row(symbols);
        }
        return rows;
    }

    public Row[] extractVerticalRows() {
        Row[] rows = new Row[Grid.GRID_SIZE];
        for(int x = 0; x < Grid.GRID_SIZE; ++x) {
            SituatedSymbol[] symbols = new SituatedSymbol[Grid.GRID_SIZE];
            for(int y = 0; y < Grid.GRID_SIZE; ++y) {
                symbols[y] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }

            rows[x] = new Row(symbols);
        }
        return rows;
    }

    public Row[] extractDiagonalHorizontalRows() {
        Row[] rows = new Row[Grid.GRID_SIZE * 2 - 1];
        int rowNum = 0;

        for(int startX = Grid.GRID_SIZE - 1; startX >= 0; --startX) {
            SituatedSymbol[] symbols = new SituatedSymbol[Grid.GRID_SIZE - startX];
            for(int x = startX, y = 0; x < Grid.GRID_SIZE; ++x, ++y) {
                symbols[y] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }
            rows[rowNum++] = new Row(symbols);
        }

        for(int startY = 1; startY < Grid.GRID_SIZE; ++startY) {
            SituatedSymbol[] symbols = new SituatedSymbol[Grid.GRID_SIZE - startY];
            for(int x = 0, y = startY; y < Grid.GRID_SIZE; ++x, ++y) {
                symbols[x] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }
            rows[rowNum++] = new Row(symbols);
        }

        return rows;
    }

    public Row[] extractDiagonalVerticalRows() {
        Row[] rows = new Row[Grid.GRID_SIZE * 2 - 1];
        int rowNum = 0;

        for(int startY = 0; startY < Grid.GRID_SIZE; ++startY) {
            SituatedSymbol[] symbols = new SituatedSymbol[startY + 1];
            for(int x = 0, y = startY; y >= 0; ++x, --y) {
                symbols[x] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }
            rows[rowNum++] = new Row(symbols);
        }

        for(int startX = 1; startX < Grid.GRID_SIZE; ++startX) {
            SituatedSymbol[] symbols = new SituatedSymbol[Grid.GRID_SIZE - startX];
            for(int x = startX, y = Grid.GRID_SIZE - 1; x < Grid.GRID_SIZE; ++x, --y) {
                symbols[Grid.GRID_SIZE - y - 1] = new SituatedSymbol(new Vector2i(x, y), this.grid.getSymbolAt(x, y));
            }
            rows[rowNum++] = new Row(symbols);
        }

        return rows;
    }

    public record Row(SituatedSymbol[] symbols) {}
}
