package cz.vixikhd.gomoku.layout.scene;

import cz.vixikhd.gomoku.layout.Board;
import cz.vixikhd.gomoku.layout.control.BoardController;

public class GameScene extends BaseScene {
	protected Board board;

	public GameScene(Board board) {
		this.board = board;
	}

	@Override
	public Board getPane() {
		return this.board;
	}

	@Override
	public BoardController getController() {
		return this.board.getController();
	}
}
