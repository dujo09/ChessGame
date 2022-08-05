package com.chessgamepro.ChessPieces.PieceMove;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;

/**
 * An implementation of the MoveCondition interface, describes
 * a simple move condition - piece can move if square moving to
 * is empty
 */
public class SimpleMoveCondition extends MoveCondition {


    @Override
    public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

        return gameBoard.isSquareEmpty(to);
    }

    @Override
    public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to){

        return new Move(pieceMoving.getPosition(), to);
    }

}
