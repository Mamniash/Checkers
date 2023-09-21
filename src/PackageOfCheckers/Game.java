package PackageOfCheckers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public abstract class Game {

    public static final int TILE_SIZE = 130;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    static boolean WhiteTurn = true;

    static final Tile[][] board = new Tile[WIDTH][HEIGHT];
    static ArrayList<Piece> PieceArrayBlack = new ArrayList<>();
    static ArrayList<Piece> PieceArrayWhite = new ArrayList<>();
    static ArrayList<Tile> TileArray = new ArrayList<>();
    private static Group TILE_GROUP = new Group();
    private static Group PIECE_GROUP = new Group();

    public static Group getPieceGroup() {
        return PIECE_GROUP;
    }

    public static Group getTileGroup() {
        return TILE_GROUP;
    }

    public static void start() {
        clean();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                TILE_GROUP.getChildren().add(tile);
                board[x][y] = tile;

                Pawn pawn = null;

                if (y >= 1 & y <= 2) {
                    pawn = new Pawn(PieceType.BLACK ,x, y);
                }

                if (y >= 5 & y <= 6) {
                    pawn = new Pawn(PieceType.WHITE ,x, y);
                }

                if (pawn != null) {
                    tile.setPiece(pawn);
                    PIECE_GROUP.getChildren().add(pawn);
                }
            }
        }
        Game.checkTurns();
    }

    static private void clean() {
        WhiteTurn = true;
        PieceArrayBlack = new ArrayList<>();
        PieceArrayWhite = new ArrayList<>();
        TileArray = new ArrayList<>();
        TILE_GROUP = new Group();
        PIECE_GROUP = new Group();
    }

    static void getToQueen(Piece piece) {
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        PIECE_GROUP.getChildren().remove(piece);
        switch (piece.getType()) {
            case WHITE:
                PieceArrayWhite.remove(piece);
                break;
            case BLACK:
                PieceArrayBlack.remove(piece);
        }
        piece = new Queen(piece.getType(), toBoard(piece.getOldX()), toBoard(piece.getOldY()));
        board[x0][y0].setPiece(piece);
        PIECE_GROUP.getChildren().add(piece);
    }

    static int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    static void turn(Piece piece, int newX, int newY) {
        MoveResult moveResult = piece.move(newX, newY);

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        switch (moveResult.getMoveType()) {
            case NONE:
                piece.abortMove();
                return;
            case TURN:
                piece.setPosition(newX, newY);
                board[newX][newY].setPiece(piece);
                board[x0][y0].setPiece(null);
                WhiteTurn = !WhiteTurn;
                break;
            case KILL:
                piece.setPosition(newX, newY);
                board[x0][y0].setPiece(null);
                board[newX][newY].setPiece(piece);

                PIECE_GROUP.getChildren().remove(moveResult.getPiece());

                switch (moveResult.getPiece().getType()) {
                    case WHITE:
                        PieceArrayWhite.remove(moveResult.getPiece());
                        break;
                    case BLACK:
                        PieceArrayBlack.remove(moveResult.getPiece());
                }

                board[toBoard(moveResult.getPiece().getOldX())]
                     [toBoard(moveResult.getPiece().getOldY())].setPiece(null);
                piece.setItKill(true);

                if (x0 < newX) piece.setLastWay(LastWay.EAST);
                    else if (x0 > newX) piece.setLastWay(LastWay.WEST);
                        else if (y0 < newY) piece.setLastWay(LastWay.NORTH);
                            else piece.setLastWay(LastWay.SOUTH);

                if (!piece.canKill(newX, newY)) {
                    piece.setItKill(false);
                    piece.setLastWay(null);
                    WhiteTurn = !WhiteTurn;
                }
        }

        if (piece.getType() == PieceType.WHITE && toBoard(piece.getOldY()) == 0
                && piece.getClass() == Pawn.class)
            getToQueen(piece);
        if (piece.getType() == PieceType.BLACK && toBoard(piece.getOldY()) == 7
                && piece.getClass() == Pawn.class)
            getToQueen(piece);

        checkTurns();
    }

    static void checkTurns() {
        for (Tile tile : TileArray) {
            tile.setOnAction(false);
        }

        int x0, y0;
        boolean isPieceCanKill = false;

        if (WhiteTurn) {
            for (Piece piece : PieceArrayWhite) {
                if (piece.canKill(x0 = toBoard(piece.getOldX()), y0 = toBoard(piece.getOldY()))) {
                    board[x0][y0].setOnAction(true);
                    isPieceCanKill = true;
                }
            }

            if (!isPieceCanKill)
                for (Piece piece : PieceArrayWhite) {
                    if (piece.canTurn(x0 = toBoard(piece.getOldX()), y0 = toBoard(piece.getOldY())))
                        board[x0][y0].setOnAction(true);
                }

            return;
        }

        for (Piece piece : PieceArrayBlack) {
            if (piece.canKill(x0 = toBoard(piece.getOldX()), y0 = toBoard(piece.getOldY()))) {
                board[x0][y0].setOnAction(true);
                isPieceCanKill = true;
            }
        }

        if (!isPieceCanKill)
            for (Piece piece : PieceArrayBlack) {
                if (piece.canTurn(x0 = toBoard(piece.getOldX()), y0 = toBoard(piece.getOldY())))
                    board[x0][y0].setOnAction(true);
        }
    }

    static Ellipse getFirstEllipse() {
        Ellipse el = new Ellipse(TILE_SIZE * 0.3125,
                TILE_SIZE * 0.26);
        el.setFill(Color.BLACK);
        el.setStroke(Color.BLACK);
        el.setStrokeWidth(TILE_SIZE * 0.03);

        el.setTranslateX
                ((TILE_SIZE - TILE_SIZE * 0.3125 * 2)/2);
        el.setTranslateY
                ((TILE_SIZE - TILE_SIZE * 0.26 * 2)/2
                        + TILE_SIZE * 0.07);
        return el;
    }

    static Ellipse getSecondEllipse(PieceType type) {
        Ellipse el = new Ellipse(TILE_SIZE * 0.3125,
                TILE_SIZE * 0.26);
        el.setFill(type == PieceType.WHITE
                ? Color.LIGHTGRAY : Color.valueOf("#737373"));
        el.setStroke(Color.BLACK);
        el.setStrokeWidth(TILE_SIZE * 0.03);

        el.setTranslateX
                ((TILE_SIZE - TILE_SIZE * 0.3125 * 2)/2);
        el.setTranslateY
                ((TILE_SIZE - TILE_SIZE * 0.26 * 2)/2);
        return el;
    }

    static Ellipse getThirdEllipse(PieceType type) {
        Ellipse el = new Ellipse(TILE_SIZE * 0.1125,
                TILE_SIZE * 0.1);
        el.setFill(type != PieceType.WHITE
                ? Color.LIGHTGRAY : Color.valueOf("#737373"));
        el.setTranslateX
                ((TILE_SIZE - TILE_SIZE * 0.3125 * 2)/2);
        el.setTranslateY
                ((TILE_SIZE - TILE_SIZE * 0.26 * 2)/2);
        return el;
    }
}
