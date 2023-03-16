package chess.domain;

import chess.domain.piece.Bishop;
import chess.domain.piece.Empty;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board generateBoard() {
        Map<Position, Piece> board = initBoard();
        return new Board(board);
    }

    private static Map<Position, Piece> initBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initEmpty(board);

        initNonPawns(Rank.ONE, Side.BLACK, board);
        initPawns(Rank.TWO, Side.BLACK, board);

        initPawns(Rank.SEVEN, Side.WHITE, board);
        initNonPawns(Rank.EIGHT, Side.WHITE, board);

        return board;
    }

    private static void initEmpty(final Map<Position, Piece> board) {
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                board.put(Position.of(file, rank), new Empty(Type.EMPTY, Side.NEUTRALITY));
            }
        }
    }

    private static void initPawns(final Rank rank, final Side side, final Map<Position, Piece> board) {
        for (File file : File.values()) {
            board.put(Position.of(file, rank), new Pawn(Type.PAWN, side));
        }
    }

    private static void initNonPawns(final Rank rank, final Side side, final Map<Position, Piece> board) {
        board.put(Position.of(File.A, rank), new Rook(Type.ROOK, side));
        board.put(Position.of(File.B, rank), new Knight(Type.KNIGHT, side));
        board.put(Position.of(File.C, rank), new Bishop(Type.BISHOP, side));
        board.put(Position.of(File.D, rank), new Queen(Type.QUEEN, side));
        board.put(Position.of(File.E, rank), new King(Type.KING, side));
        board.put(Position.of(File.F, rank), new Bishop(Type.BISHOP, side));
        board.put(Position.of(File.G, rank), new Knight(Type.KNIGHT, side));
        board.put(Position.of(File.H, rank), new Rook(Type.ROOK, side));
    }
}
