package PackageOfCheckers;

class MoveResult {

    private final MoveType moveType;
    private final Piece piece;

    MoveType getMoveType() {
        return moveType;
    }

    Piece getPiece() {
        return piece;
    }

    MoveResult(MoveType moveType, Piece piece) {
        this.moveType = moveType;
        this.piece = piece;
    }

    MoveResult(MoveType moveType) {
        this.moveType = moveType;
        this.piece = null;
    }
}
