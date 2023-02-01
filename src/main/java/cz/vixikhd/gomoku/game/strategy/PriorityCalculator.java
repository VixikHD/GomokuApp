package cz.vixikhd.gomoku.game.strategy;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class PriorityCalculator {
    public static Vector2i calculateBestMove(List<Grid.MatchedPattern> defensivePatterns, List<Grid.MatchedPattern> offensivePatterns) {
        if(defensivePatterns.isEmpty() && offensivePatterns.isEmpty()) {
            return null;
        }

        List<MoveWithPriority> possibleMoves = new ArrayList<>();
        for(Grid.MatchedPattern pattern : defensivePatterns) {
            for(Vector2i move : pattern.variation().getOutplayPositionList()) {
                possibleMoves.add(new MoveWithPriority(pattern.start().addVector(move), new Priority(Priority.Type.LOSE_PRIORITY, pattern.variation().getSymbol(move).priority())));
            }
        }
        for(Grid.MatchedPattern pattern : offensivePatterns) {
            for(Vector2i move : pattern.variation().getOutplayPositionList()) {
                possibleMoves.add(new MoveWithPriority(pattern.start().addVector(move), new Priority(Priority.Type.WIN_PRIORITY, pattern.variation().getSymbol(move).priority())));
            }
        }

        possibleMoves.sort((a, b) -> a.priority.compareWith(b.priority));

        PriorityCalculator.displayPossibleMoves(possibleMoves);

        return possibleMoves.get(0).move;
    }

    private static void displayPossibleMoves(List<MoveWithPriority> moves) {
        System.out.println("\\-----------------------------------------------------/");
        for(MoveWithPriority move : moves) {
            System.out.println(move.move.toString() + ": Priority - " + (move.priority.toString()));
        }
        System.out.println("/-----------------------------------------------------\\");
    }

    private record MoveWithPriority(Vector2i move, Priority priority) {}
}
