package cz.vixikhd.gomoku.graphic;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.BiConsumer;

public class Board extends GridPane {
    final private Vector2i dimensions;
    final private int cellSize;
    final private int borderSize;

    final private BiConsumer<Vector2i, MouseEvent> cellClickHandler;

    final private Cell[][] board;

    public Board(Vector2i dimensions, int cellSize, int borderSize, BiConsumer<Vector2i, MouseEvent> cellClickHandler) {
        this.dimensions = dimensions;
        this.cellSize = cellSize;
        this.borderSize = borderSize;

        this.cellClickHandler = cellClickHandler;

        this.board = new Cell[dimensions.getY()][dimensions.getX()];

        this.init();
    }

    private void init() {
        int sizeX = cellSize * dimensions.getX();
        int sizeY = cellSize * dimensions.getY();

        this.setMinSize(sizeX, sizeY);
        this.setMaxSize(sizeX, sizeY);

        this.setBorder(new Border(new BorderStroke(
            Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
            new CornerRadii(5),
            new BorderWidths(this.borderSize),
            new Insets(-1, 1 - this.borderSize * 2, 1 - this.borderSize * 2, -1)
        )));

        for(int y = 0; y < dimensions.getY(); ++y) {
            for(int x = 0; x < dimensions.getX(); ++x) {
                this.add(this.board[y][x] = this.initCell(x, y), x, y);
            }
        }
    }

    private Cell initCell(int x, int y) {
        return new Cell(Symbol.NONE, event -> this.handleCellClick(new Vector2i(x, y), event));
    }

    private void handleCellClick(Vector2i position, MouseEvent event) {
        if(this.cellClickHandler != null)
            this.cellClickHandler.accept(position, event);
    }

    public void clearBoard() {
        for(int y = 0; y < this.dimensions.getY(); ++y) {
            for(int x = 0; x < this.dimensions.getX(); ++x) {
                this.setSymbolAt(x, y, Symbol.NONE);
            }
        }
    }

    public void setBoardLocked() {
        this.setBoardLocked(true);
    }

    public void setBoardLocked(boolean locked) {
        for(int y = 0; y < this.dimensions.getY(); ++y) {
            for(int x = 0; x < this.dimensions.getX(); ++x) {
                this.setCellLockedAt(x, y, locked);
            }
        }
    }

    /**
     * Updates symbol at specified coordinates
     */
    public void setSymbolAt(int x, int y, Symbol symbol) {
        this.board[y][x].updateSymbol(symbol);
    }

    /**
     * Highlights cell at specified coordinates by changing its background
     */
    public void setCellHighlightedAt(int x, int y) {
        this.setCellHighlightedAt(x, y, true);
    }

    /**
     * Highlights cell at specified coordinates by changing its background
     */
    public void setCellHighlightedAt(int x, int y, boolean highlighted) {
        this.board[y][x].setHighlighted(highlighted);
    }

    /**
     * Locks the cell at specified coordinates, so it will not change background
     * on mouse enter / exit
     */
    public void setCellLockedAt(int x, int y) {
        this.setCellLockedAt(x, y, true);
    }

    /**
     * Locks the cell at specified coordinates, so it will not change background
     * on mouse enter / exit
     */
    public void setCellLockedAt(int x, int y, boolean locked) {
        this.board[y][x].setHoverDisabled(locked);
    }

    public Vector2i getDimensions() {
        return this.dimensions;
    }
}
