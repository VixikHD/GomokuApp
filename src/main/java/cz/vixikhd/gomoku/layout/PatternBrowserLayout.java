package cz.vixikhd.gomoku.layout;

import cz.vixikhd.gomoku.game.pattern.PatternCategory;
import cz.vixikhd.gomoku.game.pattern.PatternManager;
import cz.vixikhd.gomoku.layout.control.PatternBrowserController;
import cz.vixikhd.gomoku.layout.element.Button;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class PatternBrowserLayout extends VBox {
	private final PatternBrowserController controller;

	public PatternBrowserLayout() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		this.controller = new PatternBrowserController();

		PatternManager.lazyInit();

		VBox buttons = new VBox();
		buttons.setAlignment(Pos.CENTER);
		for(PatternCategory category : PatternManager.getCategories()) {
			buttons.getChildren().add(new Button(category.name()).setClickHandler(event -> this.controller.onCategoryButtonClick(event, category)));
		}

		ScrollPane scroll = new ScrollPane(buttons);
		scroll.setFitToWidth(true);

		this.getChildren().add(scroll);

		this.getChildren().add(new Button("Back to main menu").setClickHandler(this.controller::onBackToMainMenuClicked));
	}

	public PatternBrowserController getController() {
		return this.controller;
	}
}
