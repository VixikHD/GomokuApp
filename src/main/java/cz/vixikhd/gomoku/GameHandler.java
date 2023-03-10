package cz.vixikhd.gomoku;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.player.ComputerPlayer;
import cz.vixikhd.gomoku.game.player.Player;
import cz.vixikhd.gomoku.game.player.RealPlayer;

final public class GameHandler {
	private static final GameHandler instance = new GameHandler();

	private Game game;

	private GameHandler() {}

	public void startGame() {
		UserInterface.getInstance().setScene(UserInterface.GAME_GRID);

		this.startGameThread();
	}

	private void startGameThread() {
		this.game = new Game(new Player[]{
				new ComputerPlayer(Symbol.X),
				new RealPlayer(Symbol.O)
		});

		this.game.setDaemon(true);
		this.game.start();
	}

	public void shutdownGame() {
		this.game.shutdown();

		UserInterface.GAME_GRID.getPane().getBoard().resetBoard();

		UserInterface.getInstance().setScene(UserInterface.MAIN_MENU);
		UserInterface.GAME_GRID.getPane().setPlayAgainButtonVisible(false);
	}

	public static GameHandler getInstance() {
		return GameHandler.instance;
	}

	public void restartGame() {
		this.game.shutdown();
		while(this.game.isAlive()) {
			try {
				Thread.sleep(1000 / 20);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		this.startGameThread();

		UserInterface.GAME_GRID.getPane().getBoard().resetBoard();
		UserInterface.GAME_GRID.getPane().setPlayAgainButtonVisible(false);
	}
}
