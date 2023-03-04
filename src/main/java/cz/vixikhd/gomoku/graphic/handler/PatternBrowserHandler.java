package cz.vixikhd.gomoku.graphic.handler;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.PatternManager;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;
import cz.vixikhd.gomoku.graphic.Board;
import cz.vixikhd.gomoku.graphic.SmallButton;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PatternBrowserHandler {
    final private GomokuApplication app;

    private VBox root = null;

    public PatternBrowserHandler(GomokuApplication app) {
        this.app = app;
    }

    public Pane createPatternBrowser() {
        if(this.root != null) {
            return this.root;
        }

        PatternManager.init();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        for(Pattern pattern : PatternManager.getDefensivePatterns()) {
            box.getChildren().add(this.createPatternBoards(pattern));
        }

        ScrollPane scrollPane = new ScrollPane(box);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        SmallButton button = new SmallButton("Back to menu");
        button.setOnMouseClicked(event -> this.app.hidePatternBrowser());

        this.root = new VBox(10);
        this.root.setAlignment(Pos.CENTER);
        this.root.getChildren().add(scrollPane);
        this.root.getChildren().add(button);

        return this.root;
    }

    private VBox createPatternBoards(Pattern pattern) {
        VBox root = new VBox(6);
        root.setAlignment(Pos.CENTER);

        Label title = new Label(pattern.getName());
        Label description = new Label(pattern.getDescription());

        int height = 0;

        HBox box = new HBox(20);
        for(Pattern.PatternVariation variation : pattern.getVariations()) {
            PatternSymbol[][] symbols = variation.getSymbols();
            if(symbols.length > height) {
                height = symbols.length;
            }

            Board board = new Board(new Vector2i(symbols[0].length, symbols.length), 30, 5, null);
            for(int y = 0; y < symbols.length; ++y) {
                for(int x = 0; x < symbols[y].length; ++x) {
                    if(symbols[y][x].type().equals(PatternSymbol.Type.SYMBOL_PLAYER)) {
                        board.setSymbolAt(x, y, Symbol.X);
                    } else if(symbols[y][x].type().equals(PatternSymbol.Type.SYMBOL_OPPONENT)) {
                        board.setSymbolAt(x, y, Symbol.O);
                    } else if(symbols[y][x].type().equals(PatternSymbol.Type.PLACE_FOR_OUTPLAY)) {
                        board.setSymbolAt(x, y, Symbol.X);
                        board.setCellHighlightedAt(x, y);
                    }
                    board.setCellLockedAt(x, y);
                }
            }

            box.getChildren().add(board);
        }

        ScrollPane scrollPane = new ScrollPane(box);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setMaxWidth(400);
        scrollPane.setMinHeight(height * 30 + 15);

        root.getChildren().add(title);
        root.getChildren().add(description);
        root.getChildren().add(scrollPane);

        return root;
    }
}
