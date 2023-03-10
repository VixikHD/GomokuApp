package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.GameHandler;
import cz.vixikhd.gomoku.UserInterface;
import javafx.scene.input.MouseEvent;

public class MainMenuController implements Controller {
	public void onStartGameClicked(MouseEvent event) {
		GameHandler.getInstance().startGame();
	}

	public void onBrowsePatternsClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.PATTERN_BROWSER_MENU);
	}

	public void onCreditsClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.CREDITS);
	}
}
