package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;

public class Queen extends ChessPiece{

    /**
     * Constructor for a Queen
     *
     * @param position the coordinates of Queen
     * @param color the color of Queen
     */
    public Queen(Coordinate position, PieceColor color){
        super(position, PieceType.QUEEN, color);
    }

    /**
     * Constructor for Queen that takes if piece has moved as an argument
     *
     * @param position the coordinates of Queen position
     * @param color the color of Queen
     * @param hasMoved boolean saying if piece has moved before
     */
    public Queen(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.QUEEN, color, hasMoved);
    }

    /**
     * Copy constructor for Queen
     *
     * @param queen the reference Queen object by which a
     *               new one is created
     */
    public Queen(Queen queen){
        super(queen);
    }

    /**
     * Overridden method that checks if a given Queen is equal to
     * this instance of Queen
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Queen)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Overridden method for Queen that initializes the
     * movement pattern of a Queen
     */
    @Override
    public void initializeMovementPattern() {

        // Queen moves in eight directions - N, NE, E, SE, S, SW, W, NW

        SimpleMoveCondition queenMoveCondition = new SimpleMoveCondition();
        SimpleCaptureCondition queenCaptureCondition = new SimpleCaptureCondition();

        PieceMove north = new PieceMove(
                1,0,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove northEast = new PieceMove(
                1,1,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove east = new PieceMove(
                0,1,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove southEast = new PieceMove(
                -1,1,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove south = new PieceMove(
                -1,0,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove southWest = new PieceMove(
                -1,-1,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove west = new PieceMove(
                0,-1,true,
                queenMoveCondition,queenCaptureCondition
        );

        PieceMove northWest = new PieceMove(
                1,-1,true,
                queenMoveCondition,queenCaptureCondition
        );

        movePattern.add(north);
        movePattern.add(northEast);
        movePattern.add(east);
        movePattern.add(southEast);
        movePattern.add(south);
        movePattern.add(southWest);
        movePattern.add(west);
        movePattern.add(northWest);
    }

    /**
     * Overriding method for getting a copy of this Queen instance
     *
     * @return a new Queen that is identical to this instance
     */
    @Override
    public Queen getCopy(){
        return new Queen(this);
    }
}

