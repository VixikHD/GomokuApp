package cz.vixikhd.gomoku.data;

import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.PatternTransform;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;
import cz.vixikhd.gomoku.game.pattern.symbol.SymbolParser;

import java.util.ArrayList;
import java.util.List;

final public class SimplePattern extends PatternData {
	private List<String[]> variations;

	public List<Pattern> toPattern() throws PatternParseException {
		List<Pattern> patterns = new ArrayList<>();
		for (String[] variation : this.variations) {
			patterns.add(new Pattern(this.getType(), this.name, this.description, new PatternTransform(SymbolParser.parseSymbolArray(variation)).generatePatternVariations()));
		}
		return patterns;
	}
}
