package cz.vixikhd.gomoku.graphic.handler;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.game.SituatedSymbol;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.graphic.Board;
import cz.vixikhd.gomoku.graphic.SmallButton;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GridGraphicsHandler {
    final public static int CELL_SIZE = 30;
    final public static int BORDER_SIZE = 5;
    final public static int VBOX_SPACING = 12;

    final private GomokuApplication app;

    private VBox root = null;

    private SmallButton quitToMenuButton = null;

    public Vector2i lastClick = null;

    public boolean requestQuitButton = false;
    final public Queue<SituatedSymbol> updateBoardQueue;
    final public Queue<Vector2i> highlightQueue;

    private Board board = null;

    public GridGraphicsHandler(GomokuApplication app) {
        this.app = app;

        this.updateBoardQueue = new ConcurrentLinkedQueue<>();
        this.highlightQueue = new ConcurrentLinkedQueue<>();
    }

    public Pane createGrid() {
        if(this.root != null) {
            return this.root;
        }

        this.board = new Board(new Vector2i(Grid.GRID_SIZE, Grid.GRID_SIZE), CELL_SIZE, BORDER_SIZE, (position, event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                this.lastClick = position;
            }
        });

        this.quitToMenuButton = new SmallButton("Quit to menu");
        this.quitToMenuButton.setOnMouseClicked(this::handleQuitToMenuClick);
        this.quitToMenuButton.setVisible(false);

        this.root = new VBox(VBOX_SPACING);
        this.root.setAlignment(Pos.CENTER);

        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.quitToMenuButton);

        return this.root;
    }

    private void resetGrid() {
        this.board.clearBoard();
    }

    private void lockGrid() {
        this.board.setBoardLocked();
    }

    private void unlockGrid() {
        this.board.setBoardLocked(false);
    }

    private void handleQuitToMenuClick(MouseEvent event) {
        this.quitToMenuButton.setVisible(false);
        this.unlockGrid();
        this.resetGrid();

        this.app.endGame();
    }

    public void updateCellSymbol(Vector2i position, Symbol symbol) {
        this.board.setSymbolAt(position.getX(), position.getY(), symbol);
    }

    public void requestBoardUpdate(Vector2i position, Symbol symbol) {
        this.updateBoardQueue.add(new SituatedSymbol(position, symbol));
    }

    public void requestSymbolHighlight(Vector2i position) {
        this.highlightQueue.add(position);
    }

    public void collectGraphicUpdates() {
        SituatedSymbol symbol;
        while((symbol = this.updateBoardQueue.poll()) != null) {
            this.updateCellSymbol(symbol.position(), symbol.symbol());
        }

        Vector2i position;
        while ((position = this.highlightQueue.poll()) != null) {
            this.board.setCellHighlightedAt(position.getX(), position.getY());
        }

        if(this.requestQuitButton) {
            this.requestQuitButton = false;
            this.quitToMenuButton.setVisible(true);

            this.lockGrid();
        }
    }
}
