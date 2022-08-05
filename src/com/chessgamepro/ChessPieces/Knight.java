package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;

public class Knight extends ChessPiece {

    /**
     * Constructor for Knight
     *
     * @param position the coordinates of Knight
     * @param color    the color of Knight
     */
    public Knight(Coordinate position, PieceColor color) {
        super(position, PieceType.KNIGHT, color);
    }

    /**
     * Constructor for Knight that takes if piece has moved as an argument
     *
     * @param position the coordinates of Knight position
     * @param color the color of Knight
     * @param hasMoved boolean saying if piece has moved before
     */
    public Knight(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.KNIGHT, color, hasMoved);
    }

    /**
     * Copy constructor for Knight
     *
     * @param knight the reference Knight object by which a
     *               new one is created
     */
    public Knight(Knight knight){
        super(knight);
    }

    /**
     * Overridden method that checks if a given Knight is equal to
     * this instance of Knight
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Knight)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Overridden method for Knight that initializes the
     * movement pattern of a Knight
     */
    @Override
    public void initializeMovementPattern() {

        // Knight moves in L shapes all around

        SimpleMoveCondition knightMoveCondition = new SimpleMoveCondition();
        SimpleCaptureCondition knightCaptureCondition = new SimpleCaptureCondition();

        PieceMove northEastL = new PieceMove(
                2,1,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove eastNorthL = new PieceMove(
                1,2,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove eastSouthL = new PieceMove(
                -1,2,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove southEastL = new PieceMove(
                -2,1,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove southWestL = new PieceMove(
                -2,-1,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove westSouthL = new PieceMove(
                -1,-2,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove westNorthL = new PieceMove(
                1,-2,false,
                knightMoveCondition, knightCaptureCondition
        );

        PieceMove northWestL = new PieceMove(
                2,-1,false,
                knightMoveCondition, knightCaptureCondition
        );

        movePattern.add(northEastL);
        movePattern.add(eastNorthL);
        movePattern.add(eastSouthL);
        movePattern.add(southEastL);
        movePattern.add(southWestL);
        movePattern.add(westSouthL);
        movePattern.add(westNorthL);
        movePattern.add(northWestL);

    }

    /**
     * Overriding method for getting a copy of this Knight instance
     *
     * @return a new Knight that is identical to this instance
     */
    @Override
    public Knight getCopy(){
        return new Knight(this);
    }
}
