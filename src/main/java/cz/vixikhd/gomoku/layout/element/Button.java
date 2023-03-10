package cz.vixikhd.gomoku.layout.element;

import cz.vixikhd.gomoku.GomokuApplication;

public class Button extends javafx.scene.control.Button {
	public Button(String text) {
		super();

		this.setText(text);

		this.getStylesheets().add(GomokuApplication.class.getResource("layout/style/button.css").toExternalForm());
	}
}
