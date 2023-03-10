package cz.vixikhd.gomoku.layout;

import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.layout.control.GameController;
import cz.vixikhd.gomoku.layout.element.Board;
import cz.vixikhd.gomoku.layout.element.Button;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class GameLayout extends VBox {
	private static final int GRID_SIZE = Grid.GRID_SIZE;

	private final Board board;
	private final GameController controller;

	private final Button mainMenuButton;
	private final Button playAgainButton;

	public GameLayout() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		// Buttons have its own box due to different spacing
		VBox buttons = new VBox();
		buttons.setAlignment(Pos.CENTER);

		buttons.getChildren().add(this.mainMenuButton = new Button("Back to Main Menu"));
		buttons.getChildren().add(this.playAgainButton = new Button("Play Again"));

		this.getChildren().add(this.board = new Board(this.controller = new GameController(), new Vector2i(GRID_SIZE, GRID_SIZE)));
		this.getChildren().add(buttons);

		this.mainMenuButton.setOnMouseClicked(this.controller::onMainMenuButtonClicked);
		this.playAgainButton.setOnMouseClicked(this.controller::onPlayAgainButtonClicked);

		this.playAgainButton.setVisible(false);
	}

	public void setMainMenuButtonVisible(boolean state) {
		this.mainMenuButton.setVisible(state);
	}

	public void setPlayAgainButtonVisible(boolean state) {
		this.playAgainButton.setVisible(state);
	}

	public GameController getController() {
		return this.controller;
	}

	public Board getBoard() {
		return this.board;
	}
}
