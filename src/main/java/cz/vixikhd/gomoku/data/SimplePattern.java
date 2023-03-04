package cz.vixikhd.gomoku.data;

import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;
import cz.vixikhd.gomoku.game.pattern.symbol.SymbolParser;

import java.util.ArrayList;
import java.util.List;

public class SimplePattern {
    private String name;
    private String description;
    private String type;
    private List<String[]> variations;

    public List<Pattern> toPattern() throws PatternParseException {
        List<Pattern> patterns = new ArrayList<>();
        for(String[] variation : this.variations) {
            patterns.add(new Pattern(this.name, this.description, SymbolParser.parseSymbolArray(variation)));
        }
        return patterns;
    }

    public Pattern.Type getType() throws PatternParseException {
        if(this.type.equals("Offensive")) {
            return Pattern.Type.OFFENSIVE;
        } else if(this.type.equals("Defensive")) {
            return Pattern.Type.DEFENSIVE;
        }

        throw new PatternParseException("Invalid pattern type " + this.type);
    }
}
