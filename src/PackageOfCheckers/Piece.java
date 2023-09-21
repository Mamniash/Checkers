package PackageOfCheckers;

import javafx.scene.control.DialogPane;
import javafx.scene.layout.*;
import static PackageOfCheckers.Game.*;

abstract class Piece extends StackPane {

    protected final PieceType type;
    protected LastWay lastWay = null;
    protected double mouseX, mouseY;
    protected int oldX, oldY;
    protected boolean isOnActionTile = false;
    protected boolean isItKill = false;

    Piece(PieceType type, int x, int y) {
        this.type = type;

        setPosition(x, y);

        setOnMousePressed(e -> {
            isOnActionTile = board[toBoard(oldX)][toBoard(oldY)].isOnAction();
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            if (isOnActionTile) relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

        setOnMouseReleased(event -> {
            if (isOnActionTile) turn(this, toBoard(this.getLayoutX()), toBoard(this.getLayoutY()));
        });

        if (type == PieceType.WHITE) Game.PieceArrayWhite.add(this);
        else Game.PieceArrayBlack.add(this);
    }

    protected void setItKill(boolean isItKill) {
        this.isItKill= isItKill;
    }

    protected void setLastWay(LastWay lastWay) {
        this.lastWay = lastWay;
    }

    protected int getOldX() {
        return oldX;
    }

    protected int getOldY() {
        return oldY;
    }

    protected PieceType getType() {
        return type;
    }

    protected void setPosition(int x, int y) {
        relocate(oldX = x * Game.TILE_SIZE, oldY = y * Game.TILE_SIZE);
    }

    protected void abortMove() {
        relocate(oldX, oldY);
    }

    abstract MoveResult move(int newX, int newY);

    abstract boolean canKill(int x0, int y0);

    abstract boolean canTurn(int x0, int y0);
}

