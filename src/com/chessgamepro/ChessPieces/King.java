package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.MoveCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;

public class King extends ChessPiece{

    /**
     * Constructor for King
     *
     * @param position the coordinates of King
     * @param color the color of King
     */
    public King(Coordinate position, PieceColor color){
        super(position, PieceType.KING, color);
    }

    /**
     * Constructor for King that takes if piece has moved as an argument
     *
     * @param position the coordinates of King position
     * @param color the color of King
     * @param hasMoved boolean saying if piece has moved before
     */
    public King(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.KING, color, hasMoved);
    }

    /**
     * Copy constructor for King
     *
     * @param king the reference King object by which a
     *               new one is created
     */
    public King(King king){
        super(king);
    }

    /**
     * Overridden method that checks if a given King is equal to
     * this instance of King
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof King)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Overridden method for King that initializes the
     * movement pattern of a King
     */
    @Override
    public void initializeMovementPattern() {

        // King moves to squares all around - N, NE, E, SE, S, SW, W, NW

        SimpleMoveCondition kingMoveCondition = new SimpleMoveCondition();
        SimpleCaptureCondition kingCaptureCondition = new SimpleCaptureCondition();

        PieceMove north = new PieceMove(
                1,0,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove northEast = new PieceMove(
                1,1,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove east = new PieceMove(
                0,1,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove southEast = new PieceMove(
                -1,1,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove south = new PieceMove(
                -1,0,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove southWest = new PieceMove(
                -1,-1,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove west = new PieceMove(
                0,-1,false,
                kingMoveCondition, kingCaptureCondition
        );

        PieceMove northWest = new PieceMove(
                1, -1,false,
                kingMoveCondition, kingCaptureCondition
        );


        // King can castle king-side

        MoveCondition kingSideCastleCondition = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                // Get the king-side Rook
                ChessPiece kingSideRook = gameBoard.getPiece(piece -> {
                    return piece.getType() == PieceType.ROOK &&
                            piece.getColor() == pieceMoving.getColor() &&
                            ((Rook) piece).isKingSide();
                });

                // If Rook is captured, condition isn't met
                if(kingSideRook == null) {
                    return false;
                }

                // If either the king or rook have moved, condition isn't met
                if (pieceMoving.getHasMoved() || kingSideRook.getHasMoved()) {
                    return false;
                }

                Coordinate squareNextToKing = new Coordinate(pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() + 1);
                Coordinate squareNextToRook = new Coordinate(kingSideRook.getPosition().getRow(),
                        kingSideRook.getPosition().getColumn() - 1);

                // If either square between Rook and King is occupied, condition isn't met
                if (!gameBoard.isSquareEmpty(squareNextToKing) ||
                        !gameBoard.isSquareEmpty(squareNextToRook)) {
                    return false;
                }

                return true;
            }

            @Override
            public boolean testLegal(GameBoard gameBoard, ChessPiece pieceMoving, Move move) {

                Coordinate squareNextToKing =  new Coordinate(pieceMoving.getPosition().getRow(), 5);
                Coordinate squareNextToRook = new Coordinate(pieceMoving.getPosition().getRow(), 6);

                // If either square between Rook and King is in check or King
                // is in check, condition isn't met
                return !gameBoard.areSquaresInCheck(pieceMoving.getColor().getOppositeColor(),
                        squareNextToKing, squareNextToRook, pieceMoving.getPosition());
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {
                Move rookMove = new Move(
                        new Coordinate(pieceMoving.getPosition().getRow(), 7),
                        new Coordinate(pieceMoving.getPosition().getRow(), 5)
                );

                return new Move(pieceMoving.getPosition(), to, rookMove);
            }
        };

        PieceMove kingSideCastle = new PieceMove(
                0,2,false,
                kingSideCastleCondition
        );

        // King can castle queen-side

        MoveCondition queenSideCastleCondition = new MoveCondition() {

            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                //Get the queen-side Rook
                ChessPiece queenSideRook = gameBoard.getPiece(piece -> {
                    return piece.getType() == PieceType.ROOK &&
                            piece.getColor() == pieceMoving.getColor() &&
                            ((Rook) piece).isQueenSide();
                });

                // If rook is captured, condition isn't met
                if(queenSideRook == null) {
                    return false;
                }

                // If either the King or Rook have moved, condition isn't met
                if (pieceMoving.getHasMoved() || queenSideRook.getHasMoved()) {
                    return false;
                }

                Coordinate squareNextToKing = new Coordinate(pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() - 1);
                Coordinate squareNextToRook = new Coordinate(queenSideRook.getPosition().getRow(),
                        queenSideRook.getPosition().getColumn() + 1);
                Coordinate squareInMiddleOfTwo = new Coordinate(pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() - 2);

                // If any of squares between Rook and King are occupied, condition isn't met
                if (!gameBoard.isSquareEmpty(squareNextToKing) ||
                        !gameBoard.isSquareEmpty(squareNextToRook) ||
                        !gameBoard.isSquareEmpty(squareInMiddleOfTwo)) {
                    return false;
                }

                return true;
            }

            @Override
            public boolean testLegal(GameBoard gameBoard, ChessPiece pieceMoving, Move move) {

                Coordinate squareNextToKing = new Coordinate(pieceMoving.getPosition().getRow(), 3);
                Coordinate squareInMiddleOfTwo = new Coordinate(pieceMoving.getPosition().getRow(), 2);

                // If square next to King or square in middle is in check or King
                // is in check, condition isn't met
                return !gameBoard.areSquaresInCheck(pieceMoving.getColor().getOppositeColor(),
                        squareNextToKing, squareInMiddleOfTwo, pieceMoving.getPosition());
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {
                Move rookMove = new Move(
                        new Coordinate(pieceMoving.getPosition().getRow(), 0),
                        new Coordinate(pieceMoving.getPosition().getRow(), 3)
                );

                return new Move(pieceMoving.getPosition(), to, rookMove);
            }
        };

        PieceMove queenSideCastle = new PieceMove(
                0,-2,false,
                queenSideCastleCondition
        );

        movePattern.add(north);
        movePattern.add(northEast);
        movePattern.add(east);
        movePattern.add(southEast);
        movePattern.add(south);
        movePattern.add(southWest);
        movePattern.add(west);
        movePattern.add(northWest);

        movePattern.add(kingSideCastle);
        movePattern.add(queenSideCastle);
    }

    /**
     * Overriding method for getting a copy of this King instance
     *
     * @return a new King that is identical to this instance
     */
    @Override
    public King getCopy(){
        return new King(this);
    }
}
