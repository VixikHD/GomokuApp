package cz.vixikhd.gomoku.graphic;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class SmallButton extends Button {
    public SmallButton(String text) {
        super(text);

        this.setAlignment(Pos.CENTER);

        this.setMinSize(700, 80);
        this.setMaxSize(700, 80);
    }
}
