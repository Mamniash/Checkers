package PackageOfCheckers;

import static PackageOfCheckers.Game.*;

class Queen extends Piece{
    Queen(PieceType type, int x, int y) {
        super(type, x, y);

        getChildren().addAll(getFirstEllipse(), getSecondEllipse(type), getThirdEllipse(type));
    }

    MoveResult move(int newX, int newY) {
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7)
            return new MoveResult(MoveType.NONE);

        int x0 = toBoard(oldX);
        int y0 = toBoard(oldY);

        if (canKill(x0, y0, newX, newY))
            return new MoveResult(MoveType.KILL, getKilledPiece(x0, y0, newX, newY));

        if (canTurn(x0, y0, newX, newY) && !canKill(x0, y0))
            return new MoveResult(MoveType.TURN);

        return new MoveResult(MoveType.NONE);
    }

    @Override
    boolean canKill(int x0, int y0) {
        for (int i = x0 - 7; i <= 7; i++)
            for (int j = y0 - 7; j <= 7; j++)
                if (i >= 0 && j >= 0 && canKill(x0, y0, i, j))
                    return true;

        return false;
    }

    boolean canKill(int x0, int y0, int newX, int newY) {
        if (board[newX][newY].hasPiece()) return false;

        int x1 = Math.abs(newX - x0);
        int y1 = Math.abs(newY - y0);

        boolean isCanKill = true;
        boolean hasAnotherPiece = false;

        if (x1 == 0) {
            for (int i = y0 + 1; i < newY; i++) {
                if (board[x0][i].hasPiece()) {
                    if (board[x0][i].getPiece().getType() != type && !hasAnotherPiece)
                        hasAnotherPiece = true;
                    else isCanKill = false;
                }
            }

            if (isCanKill && hasAnotherPiece && lastWay != LastWay.NORTH)
                return true;
            else {
                isCanKill = true;
                hasAnotherPiece = false;
            }

            for (int i = y0 - 1; i > newY; i--) {
                if (board[x0][i].hasPiece()) {
                    if (board[x0][i].getPiece().getType() != type && !hasAnotherPiece)
                        hasAnotherPiece = true;
                    else isCanKill = false;
                }
            }

            if (isCanKill && hasAnotherPiece && lastWay != LastWay.SOUTH)
                return true;
            else {
                isCanKill = true;
                hasAnotherPiece = false;
            }
        }

        if (y1 == 0) {
            for (int i = x0 + 1; i < newX; i++)
                if (board[i][y0].hasPiece()) {
                    if (board[i][y0].getPiece().getType() != type && !hasAnotherPiece)
                        hasAnotherPiece = true;
                    else isCanKill = false;
                }

            if (isCanKill && hasAnotherPiece && lastWay != LastWay.WEST)
                return true;
            else {
                isCanKill = true;
                hasAnotherPiece = false;
            }

            for (int i = x0 - 1; i > newX; i--)
                if (board[i][y0].hasPiece()) {
                    if (board[i][y0].getPiece().getType() != type && !hasAnotherPiece)
                        hasAnotherPiece = true;
                    else isCanKill = false;
                }

            return isCanKill && hasAnotherPiece && lastWay != LastWay.EAST;
        }

        return  false;
    }

    private Piece getKilledPiece(int x0, int y0, int newX, int newY) {
        if (Math.abs(newX - x0) == 0) {
            for (int i = y0 + 1; i < newY; i++)
                if (board[x0][i].hasPiece())
                    return board[x0][i].getPiece();

            for (int i = y0 - 1; i > newY; i--)
                if (board[x0][i].hasPiece())
                    return board[x0][i].getPiece();
        }

        for (int i = x0 + 1; i < newX; i++)
            if (board[i][y0].hasPiece())
                return board[i][y0].getPiece();

        for (int i = x0 - 1; i > newX; i--)
            if (board[i][y0].hasPiece())
                return board[i][y0].getPiece();

        return null;
    }

    @Override
    boolean canTurn(int x0, int y0) {
        for (int i = x0 - 7; i <= 7; i++)
            for (int j = y0 - 7; j <= 7; j++)
                if (j >= 0 && i >= 0 && canTurn(x0, y0, i, j))
                    return true;

        return false;
    }

    boolean canTurn(int x0, int y0, int newX, int newY) {
        if (board[newX][newY].hasPiece()) return false;

        int x1 = Math.abs(newX - x0);
        int y1 = Math.abs(newY - y0);

        boolean isCanTurn = true;

        if (x1 == 0) {
            for (int i = y0 + 1; i < newY; i++)
                if (board[x0][i].hasPiece()) isCanTurn = false;

            for (int i = y0 - 1; i > newY; i--)
                if (board[x0][i].hasPiece()) isCanTurn = false;

            return isCanTurn;
        }

        if (y1 == 0) {
            for (int i = x0 + 1; i < newX; i++)
                if (board[i][y0].hasPiece()) isCanTurn = false;

            for (int i = x0 - 1; i > newX; i--)
                if (board[i][y0].hasPiece()) isCanTurn = false;

            return isCanTurn;
        }

        return false;
    }
}