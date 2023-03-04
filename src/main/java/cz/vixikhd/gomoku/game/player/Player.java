package cz.vixikhd.gomoku.game.player;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.function.Function;

public abstract class Player {
    final Symbol symbol;

    public Player(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * @return Returns player's symbol
     */
    public Symbol getSymbol() {
        return this.symbol;
    }

    /**
     * Requests a move from player. The move is given back through
     * Function<Vector2i, Boolean>, where vector is position of a new symbol.
     * Function returns whether the symbol was actually placed.
     *
     * @param targetPosRequestClosure (Vector2i) -> Bool
     */
    public abstract void requestMove(Game game, Function<Vector2i, Boolean> targetPosRequestClosure);
}
