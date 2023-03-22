package chess.domain.board;

import chess.domain.game.Turn;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.piece.Side;
import chess.domain.piece.Type;
import chess.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Board {

    public static final int LOWER_BOUNDARY = 1;
    public static final int UPPER_BOUNDARY = 8;

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(final Position source, final Position target) {
        final Piece sourcePiece = getPiece(source);
        board.put(target, sourcePiece);
        board.put(source, new Empty(Type.EMPTY, Side.NEUTRALITY));
    }

    public Side findSideByPosition(final Position position) {
        return getPiece(position).getSide();
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public List<Position> findMovablePositions(final Position source) {
        return getPiece(source).findMovablePositions(source, this);
    }

    public boolean isRightTurn(final Position source, final Turn turn) {
        final Piece piece = board.get(source);
        return isWhite(turn, piece) || isBlack(turn, piece);
    }

    private static boolean isBlack(final Turn turn, final Piece piece) {
        return piece.isBlack() && !turn.isWhite();
    }

    private static boolean isWhite(final Turn turn, final Piece piece) {
        return piece.isWhite() && turn.isWhite();
    }

    public boolean isKing(final Position target) {
        final Piece piece = board.get(target);
        return piece.isKing();
    }
}