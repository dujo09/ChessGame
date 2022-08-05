package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;

public class Rook extends ChessPiece{

    private final boolean isKingSide;

    /**
     * Constructor for Rook
     *
     * @param position the coordinates of Rook
     * @param color the color of Rook
     */
    public Rook(Coordinate position, PieceColor color){
        super(position, PieceType.ROOK, color);

        isKingSide = position.getColumn() == 7 &&
                (position.getRow() == 0 || position.getRow() == 7);
    }

    /**
     * Constructor for Rook that takes if piece has moved as an argument
     *
     * @param position the coordinates of Rook position
     * @param color the color of Rook
     * @param hasMoved boolean saying if piece has moved before
     */
    public Rook(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.ROOK, color, hasMoved);

        isKingSide = position.getColumn() == 7 &&
                (position.getRow() == 0 || position.getRow() == 7);
    }

    /**
     * Copy constructor for Rook
     *
     * @param rook the reference Rook object by which a
     *               new one is created
     */
    public Rook(Rook rook){
        super(rook);

        this.isKingSide = rook.isKingSide;
    }

    /**
     * Overridden method that checks if a given Rook is equal to
     * this instance of Rook
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rook)) {
            return false;
        }

        return isKingSide == ((Rook)obj).isKingSide &&
                super.equals(obj);
    }

    /**
     * Overridden method for Rook that initializes the
     * movement pattern of a Rook
     */
    @Override
    public void initializeMovementPattern() {

        // Rook moves in four directions - N, E, S, W

        SimpleMoveCondition rookMoveCondition = new SimpleMoveCondition();
        SimpleCaptureCondition rookCaptureCondition = new SimpleCaptureCondition();

        PieceMove north = new PieceMove(
                1,0,true,
                rookMoveCondition, rookCaptureCondition
        );

        PieceMove east = new PieceMove(
                0,1,true,
                rookMoveCondition, rookCaptureCondition
        );
        PieceMove south = new PieceMove(
                -1,0,true,
                rookMoveCondition, rookCaptureCondition
        );

        PieceMove west = new PieceMove(
                0,-1,true,
                rookMoveCondition, rookCaptureCondition
        );

        movePattern.add(north);
        movePattern.add(east);
        movePattern.add(south);
        movePattern.add(west);

    }

    // Methods for getting if this Rook is king-side or queen-side
    public boolean isKingSide(){
        return isKingSide;
    }
    public boolean isQueenSide(){
        return !isKingSide;
    }

    /**
     * Overriding method for getting a copy of this Rook instance
     *
     * @return a new Rook that is identical to this instance
     */
    @Override
    public Rook getCopy(){
        return new Rook(this);
    }

}
