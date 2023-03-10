package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.GameLayout;
import cz.vixikhd.gomoku.layout.control.GameController;

public class GameScene extends BaseScene {
	protected GameLayout pane;

	public GameScene(GameLayout pane) {
		this.pane = pane;
	}

	@Override
	public GameLayout getPane() {
		return this.pane;
	}

	@Override
	public GameController getController() {
		return this.pane.getController();
	}
}
