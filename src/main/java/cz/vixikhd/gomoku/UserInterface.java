package cz.vixikhd.gomoku;

import cz.vixikhd.gomoku.layout.GameLayout;
import cz.vixikhd.gomoku.layout.PatternBrowserLayout;
import cz.vixikhd.gomoku.layout.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.io.IOException;

final public class UserInterface {
	private static final UserInterface instance = new UserInterface();

	private final BorderPane root;

	public static final StaticScene MAIN_MENU = new MainMenuScene("main-menu.fxml");
	public static final GameScene GAME_GRID = new GameScene(new GameLayout());
	public static final PatternBrowserScene PATTERN_BROWSER_MENU = new PatternBrowserScene(new PatternBrowserLayout());
//	public static final StaticScene PATTERN_CATEGORY =
	public static final StaticScene CREDITS = new StaticScene("credits.fxml");

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
