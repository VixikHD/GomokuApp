package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.layout.control.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class StaticScene extends BaseScene {
	protected final Pane pane;
	protected final Controller controller;

	protected StaticScene(Pane pane, Controller controller) {
		this.pane = pane;
		this.controller = controller;
	}

	public StaticScene(String path) {
		URL resource = GomokuApplication.class.getResource("layout/" + path);
		if(resource == null) {
			throw new RuntimeException("Could not construct scene: Resource '" + path + "' was not found");
		}

		FXMLLoader loader = new FXMLLoader(resource);
		try {
			this.pane = loader.load();
			this.controller = loader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Pane getPane() {
		return this.pane;
	}

	@Override
	public Controller getController() {
		return this.controller;
	}
}
