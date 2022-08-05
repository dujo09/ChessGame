package com.chessgamepro.GameBoard;

import com.chessgamepro.ChessPieces.*;
import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.MoveCondition;

import java.util.ArrayList;
import java.util.List;

public final class MoveHelper {

    /**
     * Method that gets all piece moves by checking if different
     * conditions for piece to move are met
     *
     * @param piece                 the given piece
     * @param toCheckIfLegal          a boolean saying if the method should
     *                              check for the legality of the moves
     * @param toFragmentPromotionMove a boolean saying if the method should
     *                              break up promotion moves into many moves
     *                              each with a different promoted piece type
     * @return a list of all the moves
     */
    public static List<Move> getPieceMoves(ChessPiece piece, GameBoard gameBoard,
                                           boolean toCheckIfLegal, boolean toFragmentPromotionMove){
        List<PieceMove> movePattern = piece.getMovePattern();
        List<Move> moveList = new ArrayList<>();

        // Go through all piece moves in its movement pattern
        for(PieceMove pieceMove : movePattern){

            int rowCounter = piece.getPosition().getRow();
            int columnCounter = piece.getPosition().getColumn();

            while(true){
                rowCounter += pieceMove.getRowOffset();
                columnCounter += pieceMove.getColumnOffset();

                Coordinate to = new Coordinate(rowCounter, columnCounter);

                // If square goes out of bounds, break sliding
                if(gameBoard.isSquareOutOfBounds(to)){
                    break;
                }

                // Go through all conditions for this piece move and find the first one that
                // is valid given this GameBoard, then get the move tied to that condition
                for(MoveCondition moveCondition : pieceMove.getMoveConditions()){

                    if(moveCondition.testPseudoLegal(gameBoard, piece, to)){

                        Move move = moveCondition.getMove(
                                gameBoard,
                                piece,
                                to.getCopy()
                        );

                        if(toCheckIfLegal){

                            if(!moveCondition.testLegal(gameBoard, piece, move)){
                                continue;
                            }
                        }

                        if(move.getIsPromotion() && toFragmentPromotionMove){
                            for(PieceType promotedPieceType : piece.getPromotions()){
                                Move moveCopy = move.getCopy();
                                moveCopy.setPromotedPieceType(promotedPieceType);

                                moveList.add(moveCopy);

                            }
                        }else{
                            moveList.add(move);

                        }

                        break;
                    }
                }

                // If this piece move is not a sliding one or if another piece
                // was encountered, break sliding
                if(!pieceMove.isSliding() || !gameBoard.isSquareEmpty(to)){
                    break;
                }
            }
        }


        return moveList;
    }

    /**
     * Method that gets all moves of a given color
     *
     * @param color the given color
     * @param gameBoard the given game board on which
     *                     the moves are evaluated
     * @param toCheckIfLegal a boolean saying if the method should
     *                      check for the legality of the moves
     * @param toFragmentPromotionMove a boolean saying if the method should
     *                              break up promotion moves into many moves
     *                              each with a different promoted piece type
     * @return a list of legal moves
     */
    public static List<Move> getColorMoves(PieceColor color, GameBoard gameBoard,
                                           boolean toCheckIfLegal, boolean toFragmentPromotionMove){

        List<Move> movesList = new ArrayList<>();

        for(ChessPiece piece : gameBoard.getAllPieces()){

            if(piece.getColor() == color){
                movesList.addAll(getPieceMoves(piece, gameBoard, toCheckIfLegal, toFragmentPromotionMove));
            }
        }

        return movesList;
    }

}
