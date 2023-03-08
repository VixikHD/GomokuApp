package cz.vixikhd.gomoku;

import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.layout.Board;
import cz.vixikhd.gomoku.layout.scene.BaseScene;
import cz.vixikhd.gomoku.layout.scene.GameScene;
import cz.vixikhd.gomoku.layout.scene.MainMenuScene;
import cz.vixikhd.gomoku.layout.scene.StaticScene;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.io.IOException;

final public class UserInterface {
	private static final UserInterface instance = new UserInterface();

	private static final int GRID_SIZE = Grid.GRID_SIZE;

	private final BorderPane root;

	public static final StaticScene MAIN_MENU = new MainMenuScene("main-menu.fxml");
	public static final GameScene GAME_GRID = new GameScene(new Board(new Vector2i(GRID_SIZE, GRID_SIZE)));
	public static final StaticScene PATTERN_BROWSER_MENU = new StaticScene("pattern-browser-menu.fxml");

	private UserInterface() {
		try {
			this.root = (new FXMLLoader(GomokuApplication.class.getResource("layout/main-window.fxml"))).load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Font.loadFont(GomokuApplication.class.getResourceAsStream("fonts/RubikMonoOne.ttf"), 20);
	}

	public void setScene(BaseScene scene) {
		this.root.setCenter(scene.getPane());
	}

	public Parent getRootNode() {
		return this.root;
	}

	public static UserInterface getInstance() {
		return UserInterface.instance;
	}
}
