package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.GameHandler;
import cz.vixikhd.gomoku.UserInterface;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class MainMenuController implements Controller {
	public void onButtonClick(ActionEvent event) {
		UserInterface.getInstance().setScene(UserInterface.GAME_GRID);
	}

	public void onStartGameClicked(MouseEvent event) {
		GameHandler.getInstance().startGame();
	}

	public void onBrowsePatternsClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.PATTERN_BROWSER_MENU);
	}

	public void onCreditsClicked(MouseEvent event) {
		System.out.println("TODO - Add Credits Window. (Author - Vojtěch Stehlík)");
	}
}
