package cz.vixikhd.gomoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class GomokuApplication extends Application {
	private static GomokuApplication instance;

	public static void main(String[] args) {
		GomokuApplication.launch();
	}

	@Override
	public void start(Stage stage) {
		instance = this;

		// Initialize the window
		stage.setTitle("Gomoku");
		stage.getIcons().add(new Image(Objects.requireNonNull(GomokuApplication.class.getResourceAsStream("icon.png"))));
		stage.setScene(new Scene(UserInterface.getInstance().getRootNode()));
		stage.show();
	}

	public static GomokuApplication getInstance() {
		return GomokuApplication.instance;
	}
}