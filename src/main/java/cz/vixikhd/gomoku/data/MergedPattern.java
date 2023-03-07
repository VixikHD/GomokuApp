package cz.vixikhd.gomoku.data;

import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.PatternMerger;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;
import cz.vixikhd.gomoku.game.pattern.symbol.SymbolParser;

import java.util.List;

final public class MergedPattern extends PatternData {
    private int priority;

    private boolean allowTranspose;
    private boolean allowSlope;

    private List<String[]> partA;
    private List<String[]> partB;

    @Override
    public List<Pattern> toPattern() throws PatternParseException {
        PatternMerger merger = new PatternMerger(this.getType(), this.name, this.description, this.priority);
        for(String[] part : this.partA) {
            merger.addPartA(SymbolParser.parseSymbolArray(part), this.allowTranspose, this.allowSlope);
        }
        for(String[] part : this.partB) {
            merger.addPartB(SymbolParser.parseSymbolArray(part), this.allowTranspose, this.allowSlope);
        }

        return merger.generatePatterns();
    }
}
