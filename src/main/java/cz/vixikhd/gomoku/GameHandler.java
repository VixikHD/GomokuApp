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

	public boolean startGame() {
		UserInterface.getInstance().setScene(UserInterface.GAME_GRID);

		// Initialize game handler thread
		this.game = new Game(new Player[]{
			new ComputerPlayer(Symbol.X),
			new RealPlayer(Symbol.O)
		});

		this.game.setDaemon(true);
		this.game.start();

		return true;
	}

	public static GameHandler getInstance() {
		return GameHandler.instance;
	}
}
