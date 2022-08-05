package com.chessgamepro.ChessPieces.PieceMove;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;

/**
 * An implementation of the MoveCondition interface, describes
 * a simple capture condition - can move if piece of opposite
 * color is at square moving to
 */
public class SimpleCaptureCondition extends MoveCondition {

    @Override
    public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

        return gameBoard.isColorAtSquare(to, pieceMoving.getColor().getOppositeColor());
    }

    @Override
    public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

        ChessPiece capturingPiece = gameBoard.getPieceAtSquare(to);
        return new Move(pieceMoving.getPosition(), to, capturingPiece.getCopy());
    }
}
