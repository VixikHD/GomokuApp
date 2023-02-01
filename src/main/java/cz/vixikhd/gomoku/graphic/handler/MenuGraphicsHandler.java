package cz.vixikhd.gomoku.graphic.handler;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.graphic.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuGraphicsHandler {
    final private GomokuApplication app;

    private VBox menuPane = null;

    private Button startGameButton;
    private Button browsePatternsButton;
    private Button creditsButton;

    public MenuGraphicsHandler(GomokuApplication app) {
        this.app = app;
    }

    public VBox createMenu() {
        if(this.menuPane != null) {
            return this.menuPane;
        }

        VBox menu = new VBox(5);
        menu.setAlignment(Pos.CENTER);

        this.startGameButton = new MenuButton("Start game");
        this.browsePatternsButton = new MenuButton("Browse patterns");
        this.creditsButton = new MenuButton("Credits");

        this.startGameButton.setOnMouseClicked(this::handleStartGameClick);
        this.browsePatternsButton.setOnMouseClicked(this::handleBrowsePatternsClick);

        menu.getChildren().add(this.startGameButton);
        menu.getChildren().add(this.browsePatternsButton);
        menu.getChildren().add(this.creditsButton);

        return this.menuPane = menu;
    }

    private void handleStartGameClick(MouseEvent event) {
        this.app.startGame();
    }

    private void handleBrowsePatternsClick(MouseEvent event) {
        this.app.showPatterns();
    }
}
