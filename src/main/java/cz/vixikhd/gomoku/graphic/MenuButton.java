package cz.vixikhd.gomoku.graphic;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class MenuButton extends Button {
    public MenuButton(String text) {
        super(text);

        this.setAlignment(Pos.CENTER);

        this.setMinSize(500, 100);
        this.setMaxSize(500, 100);
    }
}
