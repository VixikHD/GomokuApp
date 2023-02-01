module cz.vixikhd.gomoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens cz.vixikhd.gomoku to javafx.fxml;
    exports cz.vixikhd.gomoku;
    exports cz.vixikhd.gomoku.game;
    opens cz.vixikhd.gomoku.game to javafx.fxml;
    exports cz.vixikhd.gomoku.game.grid;
    opens cz.vixikhd.gomoku.game.grid to javafx.fxml;
    exports cz.vixikhd.gomoku.math;
    opens cz.vixikhd.gomoku.math to javafx.fxml;
    exports cz.vixikhd.gomoku.game.player;
    opens cz.vixikhd.gomoku.game.player to javafx.fxml;
    exports cz.vixikhd.gomoku.game.grid.browser;
    opens cz.vixikhd.gomoku.game.grid.browser to javafx.fxml;
    exports cz.vixikhd.gomoku.game.grid.pattern;
    opens cz.vixikhd.gomoku.game.grid.pattern to javafx.fxml;
}