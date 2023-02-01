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

    public Symbol getSymbol() {
        return this.symbol;
    }

    public abstract void requestMove(Game game, Function<Vector2i, Boolean> targetPosRequestClosure);
}
