package cz.vixikhd.gomoku.data;

import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;

import java.util.List;

abstract public class PatternData {
	protected String name;
	protected String description;
	protected String type;

	public Pattern.Type getType() throws PatternParseException {
		if (this.type.equals("Offensive")) {
			return Pattern.Type.OFFENSIVE;
		} else if (this.type.equals("Defensive")) {
			return Pattern.Type.DEFENSIVE;
		}

		throw new PatternParseException("Invalid pattern type " + this.type);
	}

	abstract public List<Pattern> toPattern() throws PatternParseException;
}
