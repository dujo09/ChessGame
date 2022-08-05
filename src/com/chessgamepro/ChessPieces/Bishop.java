package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;

public class Bishop extends ChessPiece{

    /**
     * Constructor for Bishop
     *
     * @param position the coordinates of Bishop position
     * @param color the color of Bishop
     */
    public Bishop(Coordinate position, PieceColor color){
        super(position, PieceType.BISHOP, color);
    }

    /**
     * Constructor for Bishop that takes if piece has moved as an argument
     *
     * @param position the coordinates of Bishop position
     * @param color the color of Bishop
     * @param hasMoved boolean saying if piece has moved before
     */
    public Bishop(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.BISHOP, color, hasMoved);
    }

    /**
     * Copy constructor for Bishop
     *
     * @param bishop the reference Bishop object by which a
     *               new one is created
     */
    public Bishop(Bishop bishop){
        super(bishop);
    }

    /**
     * Overridden method that checks if a given Bishop is equal to
     * this instance of Bishop
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bishop)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Overridden method for Bishop that initializes the
     * movement pattern of a Bishop
     */
    @Override
    public void initializeMovementPattern() {

        // Bishop moves in four directions - NE, SE, SW, NW

        SimpleMoveCondition bishopMoveCondition = new SimpleMoveCondition();
        SimpleCaptureCondition bishopCaptureCondition = new SimpleCaptureCondition();

        PieceMove northEast = new PieceMove(
                1,1,true,
                bishopMoveCondition, bishopCaptureCondition
        );

        PieceMove southEast = new PieceMove(
                -1,1,true,
                bishopMoveCondition, bishopCaptureCondition
        );

        PieceMove southWest = new PieceMove(
                -1,-1,true,
                bishopMoveCondition, bishopCaptureCondition
        );

        PieceMove northWest = new PieceMove(
                1,-1,true,
                bishopMoveCondition, bishopCaptureCondition
        );


        movePattern.add(northEast);
        movePattern.add(southEast);
        movePattern.add(southWest);
        movePattern.add(northWest);
    }

    /**
     * Overriding method for getting a copy of this King instance
     *
     * @return a new Rook that is identical to this instance
     */
    @Override
    public Bishop getCopy(){
        return new Bishop(this);
    }
}
