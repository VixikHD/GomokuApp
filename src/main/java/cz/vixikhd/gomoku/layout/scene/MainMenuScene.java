package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.control.MainMenuController;
import javafx.scene.layout.Pane;

public class MainMenuScene extends StaticScene {
	public MainMenuScene(String path) {
		super(path);
	}

	protected MainMenuScene(Pane pane, MainMenuController controller) {
		super(pane, controller);
	}

	@Override
	public MainMenuController getController() {
		return (MainMenuController)this.controller;
	}
}
