package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.PatternCategoryLayout;
import cz.vixikhd.gomoku.layout.control.Controller;

public class PatternCategoryScene extends BaseScene {
protected PatternCategoryLayout pane;

	public PatternCategoryScene(PatternCategoryLayout pane) {
		this.pane = pane;
	}

	@Override
	public PatternCategoryLayout getPane() {
		return this.pane;
	}

	@Override
	public Controller getController() {
		return this.pane.getController();
	}
}
