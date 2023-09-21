package PackageOfCheckers;

import static PackageOfCheckers.Game.*;

class Pawn extends Piece {

    Pawn(PieceType type, int x, int y) {
        super(type, x, y);

        getChildren().addAll(getFirstEllipse(), getSecondEllipse(type));
    }

    MoveResult move(int newX, int newY) {
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7)
            return new MoveResult(MoveType.NONE);

        int x0 = toBoard(oldX);
        int y0 = toBoard(oldY);

        if (canKill(x0, y0, newX, newY))
            return new MoveResult(MoveType.KILL, board[(x0 + newX)/2][(y0 + newY)/2].getPiece());

        if (canTurn(x0, y0, newX, newY) && !canKill(x0, y0))
            return new MoveResult(MoveType.TURN);

        return new MoveResult(MoveType.NONE);
    }

    @Override
    boolean canKill(int x0, int y0) {
        if (x0 + 2 <= 7 && canKill(x0, y0, x0 + 2, y0)) return true;

        if (x0 - 2 >= 0 && canKill(x0, y0, x0 - 2, y0)) return true;

        if (y0 + 2 <= 7 && canKill(x0, y0, x0, y0 + 2)) return true;

        return y0 - 2 >= 0 && canKill(x0, y0, x0, y0 - 2);
    }

    boolean canKill(int x0, int y0, int newX, int newY) {
        if (board[newX][newY].hasPiece()) return false;

        int x1 = Math.abs(newX - x0);

        if (2 == x1 && newY == y0 && board[(x0 + newX)/2][y0].hasPiece()
                && board[(x0 + newX) / 2][y0].getPiece().getType() != type) return true;

        switch (type) {
            case WHITE:
                if (x0 == newX && y0 == newY + 2 && board[x0][y0 - 1].hasPiece()
                        && board[x0][y0 - 1].getPiece().getType() != type) return true;

                if (x0 == newX && y0 == newY - 2 && board[x0][y0 + 1].hasPiece()
                        && board[x0][y0 + 1].getPiece().getType() != type & isItKill) return true;
                break;

            case BLACK:
                if (x0 == newX && y0 == newY - 2 && board[x0][y0 + 1].hasPiece()
                        && board[x0][y0 + 1].getPiece().getType() != type) return true;

                if (x0 == newX && y0 == newY + 2 && board[x0][y0 - 1].hasPiece()
                        && board[x0][y0 - 1].getPiece().getType() != type & isItKill) return true;
            }

        return false;
    }

    @Override
    boolean canTurn(int x0, int y0) {
        if (x0 + 1 <= 7 && canTurn(x0, y0, x0 + 1, y0)) return true;

        if (x0 - 1 >= 0 && canTurn(x0, y0, x0 - 1, y0)) return true;

        if (y0 + 1 <= 7 && canTurn(x0, y0, x0, y0 + 1)) return true;

        return y0 - 1 >= 0 && canTurn(x0, y0, x0, y0 - 1);
    }

    boolean canTurn(int x0, int y0, int newX, int newY) {
        if (board[newX][newY].hasPiece()) return false;

        if (1 ==  Math.abs(newX - x0) && newY == y0) return true;

        if (getType() == PieceType.WHITE && x0 == newX && y0 == newY + 1) return true;

        return getType() == PieceType.BLACK && x0 == newX && y0 == newY - 1;
    }
}
