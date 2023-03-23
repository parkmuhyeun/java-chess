package chess.domain.piece;

import chess.domain.movepattern.KnightMovePattern;

import java.util.Arrays;

public class Knight extends ImmediatePiece {

    public Knight(final Type type, final Side side) {
        super(type, side,Arrays.asList(KnightMovePattern.values()));
    }

    @Override
    protected void validate(final Type type, final Side side) {
        validateType(type);
        validateSide(side);
    }

    private void validateType(final Type type) {
        if (type != Type.KNIGHT) {
            throw new IllegalArgumentException("나이트의 타입이 잘못되었습니다.");
        }
    }

    private void validateSide(final Side side) {
        if (side == Side.NEUTRALITY) {
            throw new IllegalArgumentException("나이트는 중립적인 기물이 아닙니다.");
        }
    }
}
