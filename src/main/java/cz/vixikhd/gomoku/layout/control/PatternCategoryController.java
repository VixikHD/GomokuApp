package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.UserInterface;
import cz.vixikhd.gomoku.layout.PatternCategoryLayout;
import javafx.scene.input.MouseEvent;

public class PatternCategoryController implements Controller {
	private final PatternCategoryLayout layout;

	public PatternCategoryController(PatternCategoryLayout layout) {
		this.layout = layout;
	}

	public void onBackToCategoriesClicked(MouseEvent event) {
		UserInterface.getInstance().setScene(UserInterface.PATTERN_BROWSER_MENU);
	}
}
