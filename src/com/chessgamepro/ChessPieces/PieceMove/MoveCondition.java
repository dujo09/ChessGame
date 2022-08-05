package com.chessgamepro.ChessPieces.PieceMove;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.ChessPieces.PieceType;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;

public abstract class MoveCondition {

    /**
     * Abstract method for testing if moving to the given coordinate on
     * the given gameBoard satisfies this move pseudo-legal condition.
     * Pseudo-legal means that it doesn't take the possibility of King
     * capture into account.
     *
     * @param gameBoard the GameBoard object on which the move is made
     * @param to        coordinate to which piece is moving
     * @return true if this condition is met
     */
    public abstract boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to);

    /**
     * Method for testing if moving to the given coordinate on the given
     * gameBoard satisfies this move legal condition. Legal means that it
     * checks if any opponent move results in King capture under this move
     * or if the King is moving through enemy controlled squares.
     *
     * @param gameBoard the GameBoard object on which the move is made
     * @param move      the move being made
     * @return true if this condition is met
     */
    public boolean testLegal(GameBoard gameBoard, ChessPiece pieceMoving, Move move){

        GameBoard gameBoardCopy = gameBoard.getCopy();
        gameBoardCopy.makeMove(move);

        ChessPiece king = gameBoardCopy.getPiece(checkingPiece -> {
            return checkingPiece.getType() == PieceType.KING &&
                    checkingPiece.getColor() == pieceMoving.getColor();
        });

        return !gameBoardCopy.areSquaresInCheck(king.getColor().getOppositeColor(), king.getPosition());
    }

    /**
     * Method for getting the move under this condition
     *
     * @param gameBoard GameBoard object on which the move is made
     * @param to        coordinate to which piece is moving
     * @return the move associated with this condition
     */
    public abstract Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to);
}
