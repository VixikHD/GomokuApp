package cz.vixikhd.gomoku.graphic.handler;

import cz.vixikhd.gomoku.GomokuApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ApplicationGraphicHandler {
    final private GomokuApplication app;

    public ApplicationGraphicHandler(GomokuApplication app) {
        this.app = app;
    }

    public VBox createTopPane() {
        ImageView view = new ImageView(new Image(GomokuApplication.class.getResourceAsStream("logo.png")));
        view.setPreserveRatio(true);
        view.setFitHeight(100);

        VBox textBox = new VBox(view);
        textBox.setAlignment(Pos.CENTER);
        textBox.setPadding(new Insets(20, 0, 20, 0));

        return textBox;
    }

    public BorderPane createRootPane() {
        BorderPane root = new BorderPane();
        root.setPrefSize(1600, 1000);

        return root;
    }
}
