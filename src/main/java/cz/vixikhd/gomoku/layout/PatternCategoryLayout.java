package cz.vixikhd.gomoku.layout;

import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.game.pattern.Pattern;
import cz.vixikhd.gomoku.game.pattern.PatternCategory;
import cz.vixikhd.gomoku.game.pattern.PatternVariation;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternSymbol;
import cz.vixikhd.gomoku.layout.control.PatternCategoryController;
import cz.vixikhd.gomoku.layout.element.Board;
import cz.vixikhd.gomoku.layout.element.Button;
import cz.vixikhd.gomoku.layout.element.Label;
import cz.vixikhd.gomoku.layout.element.Pagination;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternCategoryLayout extends VBox {
	private static final int RESERVED_SIZE_PER_ROW = 30;
	private static final int MAX_PATTERNS = 25;

	private final PatternCategoryController controller;
	private final PatternCategory category;

	private Map<Integer, ScrollPane> pages = new HashMap<>();

	private String prevDescription = "";
	private int currentPage = 0;

	public PatternCategoryLayout(PatternCategory category) {
		this.category = category;

		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		this.controller = new PatternCategoryController(this);

		this.getChildren().add(new Label(category.name()));

		int pageCount = category.patterns().size() / MAX_PATTERNS + (category.patterns().size() % MAX_PATTERNS > 0 ? 1 : 0);
		Pagination pages = new Pagination(pageCount, this.currentPage);
		pages.setPageFactory(this::generatePage);

		this.getChildren().add(pages);
		this.getChildren().add(new Button("Back to Categories").setClickHandler(this.controller::onBackToCategoriesClicked));
	}

	private ScrollPane generatePage(int page) {
		if(this.pages.containsKey(page)) {
			return this.pages.get(page);
		}

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(2);

		for(int i = page * MAX_PATTERNS, j = Math.min(i + MAX_PATTERNS, category.patterns().size()); i < j; ++i) {
			this.loadPattern(i + 1, box, category.patterns().get(i));
		}

		ScrollPane scroll = new ScrollPane(box);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);

		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		this.pages.put(page, scroll);

		return scroll;
	}

	private void loadPattern(int num, VBox box, Pattern pattern) {
		VBox patternInfo = new VBox();
		patternInfo.setSpacing(2);
		patternInfo.setAlignment(Pos.CENTER);

		if(!this.prevDescription.equals(pattern.getDescription())) {
			patternInfo.getChildren().add(new Label(pattern.getDescription()).setFontSize(10));
			this.prevDescription = pattern.getDescription();
		}
		patternInfo.getChildren().add(new Label(num + ". variation (" + (pattern.getType().equals(Pattern.Type.OFFENSIVE) ? "Offensive" : "Defensive") + ")"));

		HBox variations = new HBox();
		variations.setSpacing(16);
		variations.setAlignment(Pos.CENTER);

		double height = 0;
		for(PatternVariation variation : pattern.getVariations()) {
			PatternSymbol[][] symbols = variation.getSymbols();

			Board board = new Board(variation.getDimensions());
			for(int y = 0; y < symbols.length; ++y) {
				for(int x = 0; x < symbols[y].length; ++x) {
					PatternSymbol.Type symbolType = symbols[y][x].type();
					if(symbolType.equals(PatternSymbol.Type.SYMBOL_PLAYER)) {
						board.setSymbolAt(x, y, pattern.getType().equals(Pattern.Type.OFFENSIVE) ? Symbol.X : Symbol.O);
					} else if(symbolType.equals(PatternSymbol.Type.SYMBOL_OPPONENT)) {
						board.setSymbolAt(x, y, pattern.getType().equals(Pattern.Type.OFFENSIVE) ? Symbol.O : Symbol.X);
					} else if(symbolType.equals(PatternSymbol.Type.PLACE_FOR_OUTPLAY)) {
						board.setSymbolAt(x, y, Symbol.X);
						board.setCellHighlightedAt(x, y);
					}
				}
			}

			board.setBoardLocked(true);

			variations.getChildren().add(board);

			int size = board.getRowCount() * RESERVED_SIZE_PER_ROW + 10;
			if(size > height) {
				height = size;
			}
		}

		ScrollPane scroll = new ScrollPane(variations);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setMinViewportHeight(height);

		patternInfo.getChildren().add(scroll);

		box.getChildren().add(patternInfo);
	}

	public PatternCategoryController getController() {
		return this.controller;
	}
}
