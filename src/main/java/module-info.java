module cz.vixikhd.gomoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    exports cz.vixikhd.gomoku.data;
    exports cz.vixikhd.gomoku.game.grid.browser;
    exports cz.vixikhd.gomoku.game.grid;
    exports cz.vixikhd.gomoku.game.pattern.symbol;
    exports cz.vixikhd.gomoku.game.pattern;
    exports cz.vixikhd.gomoku.game.player;
    exports cz.vixikhd.gomoku.game;
    exports cz.vixikhd.gomoku.layout.control;
    exports cz.vixikhd.gomoku.layout.scene;
    exports cz.vixikhd.gomoku.layout;
    exports cz.vixikhd.gomoku.math;
    exports cz.vixikhd.gomoku;

    opens cz.vixikhd.gomoku to javafx.fxml;
    opens cz.vixikhd.gomoku.data to com.google.gson;
    opens cz.vixikhd.gomoku.game to javafx.fxml;
    opens cz.vixikhd.gomoku.game.grid to javafx.fxml;
    opens cz.vixikhd.gomoku.game.grid.browser to javafx.fxml;
    opens cz.vixikhd.gomoku.game.pattern to javafx.fxml;
    opens cz.vixikhd.gomoku.game.player to javafx.fxml;
    opens cz.vixikhd.gomoku.math to javafx.fxml;
    opens cz.vixikhd.gomoku.layout to javafx.fxml;
}