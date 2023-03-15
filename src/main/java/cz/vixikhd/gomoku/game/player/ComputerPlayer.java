package cz.vixikhd.gomoku.game.player;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.SituatedSymbol;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.game.grid.browser.GridBrowser;
import cz.vixikhd.gomoku.game.pattern.PatternManager;
import cz.vixikhd.gomoku.game.strategy.PriorityCalculator;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComputerPlayer extends Player {
	public ComputerPlayer(Symbol symbol) {
		super(symbol);
	}

	/**
	 * This method is here to find the last empty cell. This strategy is mostly
	 * used at end of the game, when most of the cells are used
	 */
	protected Optional<Vector2i> doLeastMove(GridBrowser browser) {
		GridBrowser.Row[] rows = browser.extractHorizontalRows();

		for (GridBrowser.Row row : rows) {
			for (SituatedSymbol symbol : row.symbols()) {
				if (symbol.symbol().equals(Symbol.NONE)) {
					return Optional.of(symbol.position());
				}
			}
		}

		return Optional.empty();
	}

	/**
	 * Finds a cell with most non-empty cells around
	 */
	protected Optional<Vector2i> doBogoMove(Grid grid) {
		HashMap<Vector2i, Integer> cellsByPriority = new HashMap<>();
		for (int y = 0; y < Grid.GRID_SIZE; ++y) {
			for (int x = 0; x < Grid.GRID_SIZE; ++x) {
				if (!grid.getSymbolAt(x, y).equals(Symbol.NONE)) {
					continue;
				}

				int priority = 0;

				for (int blockX = -1; blockX <= 1; ++blockX) {
					for (int blockY = -1; blockY <= 1; ++blockY) {
						int targetX = x + blockX;
						int targetY = y + blockY;

						if (targetX < 0 || targetX >= Grid.GRID_SIZE || targetY < 0 || targetY >= Grid.GRID_SIZE) {
							continue;
						}

						if (!grid.getSymbolAt(targetX, targetY).equals(Symbol.NONE)) {
							++priority;
						}
					}
				}

				if (priority > 0) {
					cellsByPriority.put(new Vector2i(x, y), priority);
				}
			}
		}

		if (cellsByPriority.isEmpty()) {
			return Optional.empty();
		}

		int maxPriority = -1;
		Vector2i finalPosition = null;
		for (Vector2i pos : cellsByPriority.keySet()) {
			if (finalPosition == null) {
				finalPosition = pos;
				maxPriority = cellsByPriority.get(pos);
				continue;
			}

			if (cellsByPriority.get(pos) > maxPriority) {
				finalPosition = pos;
				maxPriority = cellsByPriority.get(pos);
			}
		}

		return Optional.of(finalPosition);
	}

	/**
	 * Attempts to match all known patterns. Afterwards, choose random pattern
	 * from the list of the patterns with the highest priority.
	 *
	 * @return Returns position of a symbol, or null, if there is no pattern matched
	 */
	protected Optional<Vector2i> doPatternMove(Grid grid) {
		double time = System.currentTimeMillis();
		Optional<Vector2i> move = PriorityCalculator.calculateBestMove(
				PatternManager.getDefensivePatterns().stream()
						.map(pattern -> grid.matchPattern(this.getSymbol(), pattern))
						.flatMap(List::stream)
						.collect(Collectors.toList()
						),
				PatternManager.getOffensivePatterns().stream()
						.map(pattern -> grid.matchPattern(this.getSymbol(), pattern))
						.flatMap(List::stream)
						.collect(Collectors.toList())
		);
		time = (System.currentTimeMillis() - time) / 1000.0;

		System.out.println("Attempted to match " + PatternManager.getRegisteredVariationCount() + " pattern variations " + (move.isPresent() ? "and found a target " : "") + "in " + time + " seconds");

		return move;
	}

	protected Vector2i doMove(Grid grid) {
		if (grid.countSymbols(null) == 0) {
			return grid.center();
		}

		Optional<Vector2i> position;
		if ((position = this.doPatternMove(grid)).isPresent()) {
			return position.get();
		}

		if ((position = this.doBogoMove(grid)).isPresent()) {
			return position.get();
		}

		if((position = this.doLeastMove(grid.getBrowser())).isPresent()) {
			return position.get();
		}

		throw new RuntimeException("The grid is full");
	}

	@Override
	public void requestMove(Game game, Function<Vector2i, Boolean> targetPosRequestClosure) {
		Vector2i target = this.doMove(game.getGrid());

		if (!targetPosRequestClosure.apply(target)) {
			throw new RuntimeException("Requested move for full grid.");
		}
	}
}
