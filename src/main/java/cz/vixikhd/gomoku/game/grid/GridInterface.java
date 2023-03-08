package cz.vixikhd.gomoku.game.grid;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.math.Vector2i;

public interface GridInterface {
	public Symbol getSymbolAt(int x, int y);

	public Symbol getSymbol(Vector2i position);

	public void updateSymbolAt(int x, int y, Symbol symbol);

	public void updateSymbol(Vector2i position, Symbol symbol);
}
