package cz.vixikhd.gomoku.game;

public enum Symbol {
    NONE(0," ", null),
    X(1, "X", "x.png"),
    O(2, "O", "o.png"),
    BORDER(3, "B", null);

    private final int integerValue;
    private final String textValue;
    private final String imagePath;

    Symbol(int integerValue, String textValue, String imagePath) {
        this.integerValue = integerValue;
        this.textValue = textValue;
        this.imagePath = imagePath;
    }

    public Symbol opponent() {
        if(this.equals(Symbol.X)) {
            return Symbol.O;
        } else if(this.equals(Symbol.O)) {
            return Symbol.X;
        }

        throw new RuntimeException("Attempted to get opponent for undisplayable symbol");
    }

    public int getIntegerValue() {
        return this.integerValue;
    }

    public String getTextValue() {
        return this.textValue;
    }

    public String getImagePath() {
        return this.imagePath == null ? null : "symbols/" + this.imagePath;
    }
}
