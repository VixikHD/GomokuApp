package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.control.Controller;
import javafx.scene.layout.Pane;

abstract public class BaseScene {
	abstract public Pane getPane();

	abstract public Controller getController();
}
