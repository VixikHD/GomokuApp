package cz.vixikhd.gomoku.game;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.game.grid.Grid;
import cz.vixikhd.gomoku.game.grid.browser.GridBrowser;
import cz.vixikhd.gomoku.game.grid.pattern.PatternManager;
import cz.vixikhd.gomoku.game.player.Player;
import cz.vixikhd.gomoku.math.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Thread {
    final private static Symbol[] SYMBOLS = {Symbol.X, Symbol.O};

    final private static Random random = new Random(System.currentTimeMillis());

    final private GomokuApplication ui;
    final private Grid grid;

    final private Player[] players;

    private int moveNumber = 0;

    private boolean ended = false;
    private Symbol winner = Symbol.NONE;

    public Game(GomokuApplication ui, Player[] players) {
        if(players.length != 2) {
            throw new IllegalArgumentException("There must be 2 players in the array.");
        }

        this.ui = ui;
        this.grid = Grid.emptyGrid();
        this.players = players;
    }

    public void run() {
        PatternManager.init();

        while(true) {
            if(!this.play()) {
                break;
            }

            ++this.moveNumber;
        }
    }

    /**
     * @return Returns whether the game continues
     */
    private boolean play() {
        Player playerOnMove = this.players[this.moveNumber & 1];
        Player opponent = this.players[(this.moveNumber + 1) & 1];

        playerOnMove.requestMove(this, target -> {
            if(!this.grid.getSymbol(target).equals(Symbol.NONE)) {
                return false;
            }

            // Update backend
            this.grid.updateSymbol(target, playerOnMove.getSymbol());
            // Update UI
            this.ui.requestBoardUpdate(target, playerOnMove.getSymbol());

            List<Vector2i> winRow = new ArrayList<>();
            Symbol winner = findFiveInRow(winRow);
            if(!winner.equals(Symbol.NONE)) {
                if(this.ended) {
                    throw new RuntimeException("The game has already ended");
                }

                this.ended = true;
                this.winner = winner;

                for(Vector2i pos : winRow) {
                    this.ui.requestSymbolHighlight(pos);
                }

                this.ui.requestShowQuitButton();

                System.out.println("Game ended. Winner - " + this.winner);
                return true;
            }

            return true;
        });

        // TODO
        return !this.ended;
    }

    private Symbol findFiveInRow(List<Vector2i> symbols) {
        GridBrowser.Row[] rows = this.grid.getBrowser().extractAllRows();
        for(GridBrowser.Row row : rows) {
            if(row.symbols().length < 5) {
                continue;
            }

            Symbol lastSymbol = Symbol.NONE;
            int count = 0;

            int i = 0;
            for(SituatedSymbol symbol : row.symbols()) {
                if(symbol.symbol().equals(Symbol.NONE)) {
                    lastSymbol = Symbol.NONE;
                    count = 0;
                    ++i;
                    continue;
                }

                if(symbol.symbol().equals(lastSymbol)) {
                    if(++count == 5) {
                        for(int j = 0; j < 5; ++j) {
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

    public GomokuApplication getUi() {
        return this.ui;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public static Random getRandom() {
        return Game.random;
    }
}
