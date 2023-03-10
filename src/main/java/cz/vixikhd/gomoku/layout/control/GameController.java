package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.GameHandler;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.scene.input.MouseEvent;

public class GameController implements Controller {
	public volatile Vector2i lastClick = null;

	public void handleCellClicked(MouseEvent event, Vector2i position) {
		this.lastClick = position;
	}

	public void onMainMenuButtonClicked(MouseEvent event) {
		GameHandler.getInstance().shutdownGame();
	}

	public void onPlayAgainButtonClicked(MouseEvent event) {
		GameHandler.getInstance().restartGame();
	}
}
