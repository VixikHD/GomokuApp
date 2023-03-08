package cz.vixikhd.gomoku.layout.control;

import cz.vixikhd.gomoku.math.Vector2i;
import javafx.scene.input.MouseEvent;

public class BoardController implements Controller {
	public volatile Vector2i lastClick = null;

	public void handleCellClicked(MouseEvent event, Vector2i position) {
		this.lastClick = position;
	}
}
