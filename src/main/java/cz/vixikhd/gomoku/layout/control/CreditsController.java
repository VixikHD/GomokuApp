package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.UserInterface;
import javafx.scene.input.MouseEvent;

public class CreditsController implements Controller {
	public void onBackToMainMenuClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.MAIN_MENU);
	}
}
