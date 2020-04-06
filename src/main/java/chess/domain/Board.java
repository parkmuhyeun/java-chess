package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceFactory;
import chess.domain.piece.Team;
import chess.exception.IllegalMoveException;
import chess.exception.NullPieceException;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private static final String SOURCE_SAME_WITH_DESTINATION = "말이 원래 있던 자리입니다.";
    private static final String NO_PIECE_IN_SOURCE = "해당 위치에 말이 없습니다.";
    private static final String SAME_TEAM_PIECE_IN_DESTINATION = "해당 자리에 같은 팀 말이 있기 때문에 말을 움직일 수 없습니다!";

    private Pieces pieces;
    private Turn turn;

    public Board() {
        this(new Pieces(PieceFactory.getInstance().getPieces()), new Turn(Team.WHITE));
    }

    public Board(Pieces pieces, Turn turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    public void movePiece(Position source, Position destination) {
        validateSameDestination(source, destination);
        Piece sourcePiece = pieces.findByPosition(source);
        validateSource(sourcePiece);
        Piece destinationPiece = pieces.findByPosition(destination);
        List<Piece> piecesInBetween = source.getPositionsInBetween(destination).stream()
                .map(pieces::findByPosition)
                .collect(Collectors.toList());
        sourcePiece.validateDestination(destination, destinationPiece, piecesInBetween);
        pieces.validateMove(source, destination);
        if (destinationPiece != null) {
            killPiece(sourcePiece, destinationPiece);
        }
        pieces.move(source, destination);
        this.turn = turn.changeTurn();
    }

    private void validateSameDestination(Position source, Position destination) {
        if (source.equals(destination)) {
            throw new IllegalMoveException(SOURCE_SAME_WITH_DESTINATION);
        }
    }

    private void validateSource(Piece piece) {
        if (piece == null) {
            throw new NullPieceException(NO_PIECE_IN_SOURCE);
        }
        if (!turn.isTurnOf(piece.getTeam())) {
            throw new IllegalMoveException("현재 " + turn + "의 차례입니다!");
        }
    }

    private void killPiece(Piece piece, Piece destinationPiece) {
        if (piece.isSameTeam(destinationPiece)) {
            throw new IllegalMoveException(SAME_TEAM_PIECE_IN_DESTINATION);
        }
        pieces.killPiece(destinationPiece);
    }

    public double calculateScoreByTeam(Team team) {
        return new TotalScore(pieces.getAlivePiecesByTeam(team)).getTotalScore();
    }

    public boolean isBothKingAlive() {
        return pieces.isBothKingAlive();
    }

    public Pieces getPieces() {
        return pieces;
    }

    public Team getWinner() {
        return pieces.teamWithAliveKing();
    }

    public Turn getTurn() {
        return turn;
    }

    public void reset() {
        this.pieces = new Pieces(PieceFactory.getInstance().getPieces());
    }
}
