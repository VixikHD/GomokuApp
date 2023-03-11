package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.PatternBrowserLayout;
import cz.vixikhd.gomoku.layout.control.Controller;
import javafx.scene.layout.Pane;

public class PatternBrowserScene extends BaseScene {
	protected PatternBrowserLayout pane;

	public PatternBrowserScene(PatternBrowserLayout pane) {
		this.pane = pane;
	}

	@Override
	public Pane getPane() {
		return this.pane;
	}

	@Override
	public Controller getController() {
		return this.pane.getController();
	}
}
