package cz.vixikhd.gomoku.game;

import cz.vixikhd.gomoku.UserInterface;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.game.grid.browser.GridBrowser;
import cz.vixikhd.gomoku.game.pattern.PatternManager;
import cz.vixikhd.gomoku.game.player.Player;
import cz.vixikhd.gomoku.math.Vector2i;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Thread {
	final private static Random random = new Random(System.currentTimeMillis());

	final private Grid grid;

	final private Player[] players;

	private int moveNumber = 0;

	private boolean shutdown = false;
	private boolean ended = false;

	public Game(Player[] players) {
		if (players.length != 2) {
			throw new IllegalArgumentException("There must be 2 players in the array.");
		}

		this.grid = Grid.emptyGrid();
		this.players = players;
	}

	public void run() {
		PatternManager.lazyInit();

		while(!this.shutdown && this.play()) {
			++this.moveNumber;
		}
	}

	/**
	 * Notifies player on move to proceed a move
	 *
	 * @return Returns whether the game continues
	 */
	private boolean play() {
		Player playerOnMove = this.players[this.moveNumber & 1];

		playerOnMove.requestMove(this, target -> {
			if (!this.grid.getSymbol(target).equals(Symbol.NONE)) {
				return false;
			}

			// Update backend
			this.grid.updateSymbol(target, playerOnMove.getSymbol());
			// Update UI
			Platform.runLater(() -> UserInterface.GAME_GRID.getPane().getBoard().setSymbolAt(target.getX(), target.getY(), playerOnMove.getSymbol()));

			if (this.testEnd())
				return true;

			return true;
		});

		// TODO - Wtf is this
		return !this.ended;
	}

	private boolean testEnd() {
		List<Vector2i> winRow = new ArrayList<>();
		Symbol winner = findFiveInRow(winRow);
		if (!winner.equals(Symbol.NONE)) {
			if (this.ended) {
				throw new RuntimeException("The game has already ended");
			}

			this.ended = true;

			// UI
			Platform.runLater(() -> {
				for(Vector2i pos : winRow) {
					UserInterface.GAME_GRID.getPane().getBoard().setCellHighlightedAt(pos.getX(), pos.getY());
					UserInterface.GAME_GRID.getPane().getBoard().setCellLockedAt(pos.getX(), pos.getY());
				}

				UserInterface.GAME_GRID.getPane().setPlayAgainButtonVisible(true);
			});

			System.out.println("Game ended. Winner - " + winner);
			return true;
		}

		return false;
	}

	private Symbol findFiveInRow(List<Vector2i> symbols) {
		GridBrowser.Row[] rows = this.grid.getBrowser().extractAllRows();
		for (GridBrowser.Row row : rows) {
			if (row.symbols().length < 5) {
				continue;
			}

			Symbol lastSymbol = Symbol.NONE;
			int count = 0;

			int i = 0;
			for (SituatedSymbol symbol : row.symbols()) {
				if (symbol.symbol().equals(Symbol.NONE)) {
					lastSymbol = Symbol.NONE;
					count = 0;
					++i;
					continue;
				}

				if (symbol.symbol().equals(lastSymbol)) {
					if (++count == 5) {
						for (int j = 0; j < 5; ++j) {
							symbols.add(row.symbols()[i - j].position());
						}

						return lastSymbol;
					}
				} else {
					count = 1;
					lastSymbol = symbol.symbol();
				}
				++i;
			}
		}

		return Symbol.NONE;
	}

	public void shutdown() {
		this.shutdown = true;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public static Random getRandom() {
		return Game.random;
	}
}
