package PackageOfCheckers;

import static PackageOfCheckers.Game.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Tile extends Rectangle {

    private Piece piece;

    private static final Color COLOR_WHITE = Color.valueOf("#FFDB80");
    private static final Color COLOR_BLACK = Color.valueOf("#414141");
    private static final Color COLOR_WHITE_ACTION = Color.valueOf("#FF8F40");
    private static final Color COLOR_BLACK_ACTION = Color.valueOf("#A64500");

    private final boolean isWhite;
    private boolean onAction;

    boolean hasPiece() {
        return piece != null;
    }

    boolean isOnAction() {
        return onAction;
    }

    void setOnAction(boolean onAction) {
        this.onAction = onAction;
        if (onAction) {
            setFill(isWhite ? COLOR_WHITE_ACTION : COLOR_BLACK_ACTION);
        } else setFill(isWhite ? COLOR_WHITE : COLOR_BLACK);
    }

    Piece getPiece() {
        return piece;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    Tile(boolean isWhite, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        this.isWhite = isWhite;
        this.setOnAction(false);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        Game.TileArray.add(this);
    }
}
