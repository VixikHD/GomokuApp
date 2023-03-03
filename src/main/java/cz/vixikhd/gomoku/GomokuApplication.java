package cz.vixikhd.gomoku;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.player.ComputerPlayer;
import cz.vixikhd.gomoku.game.player.Player;
import cz.vixikhd.gomoku.game.player.RealPlayer;
import cz.vixikhd.gomoku.graphic.handler.ApplicationGraphicHandler;
import cz.vixikhd.gomoku.graphic.handler.GridGraphicsHandler;
import cz.vixikhd.gomoku.graphic.handler.MenuGraphicsHandler;
import cz.vixikhd.gomoku.graphic.handler.PatternBrowserHandler;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class GomokuApplication extends Application {
    private static GomokuApplication instance;

    private ApplicationGraphicHandler applicationGraphicHandler;
    private GridGraphicsHandler gridGraphicsHandler;
    private MenuGraphicsHandler menuGraphicsHandler;
    private PatternBrowserHandler patternBrowserHandler;

    private BorderPane rootView;

    private Game game = null;

    public static void main(String[] args) {
        GomokuApplication.launch();
    }

    @Override
    public void start(Stage stage) {
        GomokuApplication.instance = this;

        // Initialize properties
        this.applicationGraphicHandler = new ApplicationGraphicHandler(this);
        this.gridGraphicsHandler = new GridGraphicsHandler(this);
        this.menuGraphicsHandler = new MenuGraphicsHandler(this);
        this.patternBrowserHandler = new PatternBrowserHandler(this);

        // Initialize the window
        stage.setTitle("Gomoku");
        stage.getIcons().add(new Image(Objects.requireNonNull(GomokuApplication.class.getResourceAsStream("icon.png"))));
        stage.setScene(this.createSceneContent());
        stage.show();

        // Initialize application timer
        new Timer().schedule(new ApplicationTimer(this), 1000 / 20, 1000 / 20);
    }

    private void tick() {
        this.gridGraphicsHandler.collectGraphicUpdates();
    }

    private Scene createSceneContent() {
        this.rootView = this.applicationGraphicHandler.createRootPane();
        this.rootView.setTop(this.applicationGraphicHandler.createTopPane());
        this.rootView.setCenter(this.menuGraphicsHandler.createMenu());

        Scene scene = new Scene(this.rootView);

        // Style...
        Font.loadFont(GomokuApplication.class.getResourceAsStream("fonts/RubikMonoOne.ttf"), 20);
        scene.getStylesheets().add(Objects.requireNonNull(GomokuApplication.class.getResource("stylesheet.css")).toExternalForm());

        return scene;
    }

    public boolean startGame() {
        this.rootView.setCenter(this.gridGraphicsHandler.createGrid());

        // Initialize game handler thread
        this.game = new Game(GomokuApplication.instance, new Player[] {
            new ComputerPlayer(Symbol.X),
            new RealPlayer(Symbol.O)
        });

        this.game.setDaemon(true);
        this.game.start();

        return true;
    }

    public void showPatterns() {
        this.rootView.setCenter(this.patternBrowserHandler.createPatternBrowser());
    }

    public boolean endGame() {
        this.rootView.setCenter(this.menuGraphicsHandler.createMenu());
        this.game = null;
        return true;
    }

    public void hidePatternBrowser() {
        this.rootView.setCenter(this.menuGraphicsHandler.createMenu());
    }

    public void requestBoardUpdate(Vector2i position, Symbol symbol) {
        this.gridGraphicsHandler.requestBoardUpdate(position, symbol);
    }

    public void requestShowQuitButton() {
        this.gridGraphicsHandler.requestQuitButton = true;
    }

    public void requestSymbolHighlight(Vector2i position) {
        this.gridGraphicsHandler.requestSymbolHighlight(position);
    }

    public Vector2i getLastBoardClickPos() {
        return this.gridGraphicsHandler.lastClick;
    }

    public void resetLastBoardClickPos() {
        this.gridGraphicsHandler.lastClick = null;
    }

    private static class ApplicationTimer extends TimerTask {
        final private GomokuApplication app;

        public ApplicationTimer(GomokuApplication app) {
            this.app = app;
        }

        @Override
        public void run() {
            Platform.runLater(app::tick);
        }
    }
}