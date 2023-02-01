package cz.vixikhd.gomoku.graphic;

import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.game.Symbol;
import cz.vixikhd.gomoku.graphic.handler.GridGraphicsHandler;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cell extends StackPane {
    private ImageView displayedSymbol = null;
    private boolean hoverDisabled = false;

    public Cell(Symbol symbol, EventHandler<MouseEvent> clickHandler) {
        this.setMinSize(GridGraphicsHandler.CELL_SIZE, GridGraphicsHandler.CELL_SIZE);
        this.setMaxSize(GridGraphicsHandler.CELL_SIZE, GridGraphicsHandler.CELL_SIZE);

        this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.hoverProperty().addListener((observable, oldValue, value) -> {
            if(value) {
                if(this.displayedSymbol == null && !this.hoverDisabled) {
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240, 1), null, null)));
                }
            } else if(!this.hoverDisabled) {
                this.setBackground(Background.EMPTY);
            }
        });

        this.setOnMouseClicked(clickHandler);
        this.setAlignment(Pos.CENTER);

        this.updateSymbol(symbol);
    }

    public void setHighlighted(boolean highlighted) {
        if(highlighted) {
            this.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200, 1), null, null)));
        } else {
            this.setBackground(Background.EMPTY);
        }
    }

    public void updateSymbol(Symbol symbol) {
        if(symbol.getImagePath() == null) {
            this.getChildren().remove(this.displayedSymbol);
            this.displayedSymbol = null;
            this.setBackground(Background.EMPTY);
            return;
        }

        Image image = new Image(GomokuApplication.class.getResourceAsStream(symbol.getImagePath()));
        if(this.displayedSymbol == null) {
            this.displayedSymbol = new ImageView(image);
            this.displayedSymbol.fitWidthProperty().bind(this.widthProperty().divide(1.4));
            this.displayedSymbol.fitHeightProperty().bind(this.heightProperty().divide(1.4));

            this.getChildren().add(this.displayedSymbol);
        }

        this.displayedSymbol.setImage(image);
    }

    public void setHoverDisabled(boolean hoverDisabled) {
        this.hoverDisabled = hoverDisabled;
    }
}
