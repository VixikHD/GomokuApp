package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.UserInterface;
import cz.vixikhd.gomoku.game.pattern.PatternCategory;

import cz.vixikhd.gomoku.layout.PatternCategoryLayout;
import cz.vixikhd.gomoku.layout.scene.PatternCategoryScene;
import javafx.scene.input.MouseEvent;

public class PatternBrowserController implements Controller {

	public void onCategoryButtonClick(MouseEvent event, PatternCategory category) {
		UserInterface.getInstance().setScene(new PatternCategoryScene(new PatternCategoryLayout(category)));
	}

	public void onBackToMainMenuClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.MAIN_MENU);
	}
}
