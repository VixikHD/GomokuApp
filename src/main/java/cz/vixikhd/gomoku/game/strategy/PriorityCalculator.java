package cz.vixikhd.gomoku.game.strategy;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriorityCalculator {
	public static Optional<Vector2i> calculateBestMove(List<Grid.MatchedPattern> defensivePatterns, List<Grid.MatchedPattern> offensivePatterns) {
		if (defensivePatterns.isEmpty() && offensivePatterns.isEmpty()) {
			return Optional.empty();
		}

		List<MoveWithPriority> possibleMoves = new ArrayList<>();
		for (Grid.MatchedPattern pattern : defensivePatterns) {
			for (Vector2i move : pattern.variation().getOutplayPositionList()) {
				possibleMoves.add(new MoveWithPriority(pattern.start().addVector(move), new Priority(Priority.Type.LOSE, pattern.variation().getSymbol(move).priority())));
			}
		}
		for (Grid.MatchedPattern pattern : offensivePatterns) {
			for (Vector2i move : pattern.variation().getOutplayPositionList()) {
				possibleMoves.add(new MoveWithPriority(pattern.start().addVector(move), new Priority(Priority.Type.WIN, pattern.variation().getSymbol(move).priority())));
			}
		}

		Priority priority = possibleMoves.get(0).priority;

		List<MoveWithPriority> availableMoves = new ArrayList<>();
		availableMoves.add(possibleMoves.get(0));

		for (int i = 1; i < possibleMoves.size(); ++i) {
			MoveWithPriority move = possibleMoves.get(i);
			int cmp = move.priority.compareWith(priority);
			if (cmp < 0) {
				availableMoves.clear();
				availableMoves.add(move);
				priority = move.priority;
			} else if (cmp == 0) {
				availableMoves.add(move);
			}
		}

		PriorityCalculator.displayPossibleMoves(possibleMoves, availableMoves);

		return Optional.of(availableMoves.get(Game.getRandom().nextInt(availableMoves.size())).move);
	}

	private static void displayPossibleMoves(List<MoveWithPriority> possible, List<MoveWithPriority> available) {
		System.out.println("\\-----------------------------------------------------/");
		System.out.println("Possible:\n");
		for (MoveWithPriority move : possible) {
			System.out.println(move.move.toString() + ": Priority - " + (move.priority.toString()));
		}
		System.out.println("Filtered by priority:\n");
		for (MoveWithPriority move : available) {
			System.out.println(move.move.toString() + ": Priority - " + (move.priority.toString()));
		}
		System.out.println("/-----------------------------------------------------\\");
	}

	private record MoveWithPriority(Vector2i move, Priority priority) {
	}
}
