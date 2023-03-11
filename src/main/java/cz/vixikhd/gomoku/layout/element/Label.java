package cz.vixikhd.gomoku.layout.element;

import cz.vixikhd.gomoku.GomokuApplication;
import javafx.scene.text.Font;

public class Label extends javafx.scene.control.Label {
	public Label(String text) {
		super();

		this.setText(text);

		this.getStylesheets().add(GomokuApplication.class.getResource("layout/style/label.css").toExternalForm());
	}

	public Label setFontSize(int size) {
		this.setStyle("-fx-font-size: " + size + ";");

		return this;
	}
}
