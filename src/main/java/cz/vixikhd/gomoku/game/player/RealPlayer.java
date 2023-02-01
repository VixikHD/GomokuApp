package cz.vixikhd.gomoku.game.player;

import cz.vixikhd.gomoku.game.Game;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.function.Function;

public class RealPlayer extends Player {
    public RealPlayer(Symbol symbol) {
        super(symbol);
    }

    @Override
    public void requestMove(Game game, Function<Vector2i, Boolean> targetPosRequestClosure) {
        game.getUi().resetLastBoardClickPos();

        do {
            try {
                Thread.sleep(1000 / 20);
            } catch (InterruptedException e) {}

            if(game.getUi().getLastBoardClickPos() == null) {
                continue;
            }

            if(targetPosRequestClosure.apply(game.getUi().getLastBoardClickPos())) {
                break;
            }

            game.getUi().resetLastBoardClickPos();
        } while (true);
    }
}