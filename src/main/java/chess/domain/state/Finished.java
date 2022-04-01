package chess.domain.state;

import chess.domain.Team;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;

public class Finished implements GameState {
    private final Team team;
    private final Map<Position, Piece> board;

    public Finished(Team team, Map<Position, Piece> board) {
        this.team = team;
        this.board = board;
    }

    @Override
    public Piece getPiece(Position position) {
        return null;
    }

    @Override
    public Playing move(String source, String destination) {
        return null;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return board;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
