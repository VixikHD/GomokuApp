package cz.vixikhd.gomoku.game.pattern;

import java.util.List;
import java.util.stream.Collectors;

public record PatternCategory(String name, Pattern.Type type, List<Pattern> patterns) {
	public List<Pattern> getOffensivePatterns() {
		return this.patterns.stream().filter(pattern -> pattern.getType().equals(Pattern.Type.OFFENSIVE)).collect(Collectors.toList());
	}

	public List<Pattern> getDefensivePatterns() {
		return this.patterns.stream().filter(pattern -> pattern.getType().equals(Pattern.Type.DEFENSIVE)).collect(Collectors.toList());
	}
}
