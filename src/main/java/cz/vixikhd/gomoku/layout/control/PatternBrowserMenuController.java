package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.UserInterface;
import javafx.scene.input.MouseEvent;

public class PatternBrowserMenuController implements Controller {
	public void onSimplePatternsClicked(MouseEvent event) {
	}

	public void onMergedPatternsClicked(MouseEvent event) {
	}

	public void onBackToMainMenuClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.MAIN_MENU);
	}
}
