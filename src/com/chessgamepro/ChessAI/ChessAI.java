package com.chessgamepro.ChessAI;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.ChessPieces.PieceType;
import com.chessgamepro.Game.Game;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;
import com.chessgamepro.GameBoard.MoveHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChessAI extends SwingWorker<Move, Void> {

    // Constants representing the value of each piece
    public static final int KING_VALUE = 1000;
    public static final int QUEEN_VALUE = 900;
    public static final int ROOK_VALUE = 500;
    public static final int BISHOP_VALUE = 300;
    public static final int KNIGHT_VALUE = 300;
    public static final int PAWN_VALUE = 100;

    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = Integer.MIN_VALUE;

    private final Game game;
    private final List<Move> moveList;
    private final boolean isWhite;
    private final int searchDepth;


    public ChessAI(Game game, List<Move> moveList, boolean isWhite,  int searchDepth){
        this.game = game;
        this.moveList = moveList;
        this.isWhite = isWhite;
        this.searchDepth = searchDepth;

    }

    public Move getBestMove(){

        GameBoard gameBoardCopy = game.getGameBoard().getCopy();

        Move bestMove = null;
        int bestMoveScore = isWhite ? MIN : MAX;

        for(Move move : moveList){
            gameBoardCopy.makeMove(move);

            int moveScore = miniMax(
                    gameBoardCopy,
                    MIN,
                    MAX,
                    searchDepth,
                    isWhite
            );

            // Debugging
            System.out.println("AI move " + move + ", score is " + moveScore);

            if(isWhite){
                if(moveScore > bestMoveScore){

                    bestMoveScore = moveScore;
                    bestMove = move;
                }
            }else{
                if(moveScore < bestMoveScore){

                    bestMoveScore = moveScore;
                    bestMove = move;
                }
            }

            gameBoardCopy.undoMove();
        }

        return bestMove;
    }

    public static int miniMax(GameBoard gameBoard, int alpha, int beta, int depth, boolean isMaximizer){

        PieceColor oppositeColor = isMaximizer ? PieceColor.BLACK : PieceColor.WHITE;

        List<Move> oppositeColorMoves =
                MoveHelper.getColorMoves(oppositeColor, gameBoard, true, true);

        if(oppositeColorMoves.size() == 0){
            return isMaximizer ? MAX : MIN;
        }

        orderMoves(oppositeColorMoves, gameBoard);

        if(depth == 0){
            return evaluateBoard(gameBoard);
        }

        GameBoard gameBoardCopy = gameBoard.getCopy();

        if(isMaximizer) {
            int maxEvaluation = MIN;

            for (Move move : oppositeColorMoves) {
                gameBoardCopy.makeMove(move);

                int evaluation = miniMax(gameBoardCopy, alpha, beta, depth - 1, false);
                maxEvaluation = Math.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha, evaluation);

                // Debugging
                //System.out.println("Black move " + move.toString() + " evaluated. Score is " + evaluation);

                if(beta <= alpha){
                    break;
                }

                gameBoardCopy.undoMove();
            }
            return maxEvaluation;
        }else {
            int minEvaluation = MAX;

            for (Move move : oppositeColorMoves) {
                gameBoardCopy.makeMove(move);

                int evaluation = miniMax(gameBoardCopy, alpha, beta, depth - 1, true);
                minEvaluation = Math.min(minEvaluation, evaluation);
                beta = Math.min(beta, evaluation);

                // Debugging
                //System.out.println("White move " + move.toString() + " evaluated. Score is " + evaluation);

                if(beta <= alpha){
                    break;
                }

                gameBoardCopy.undoMove();
            }
            return minEvaluation;
        }
    }
    private static int evaluateBoard(GameBoard gameBoard){

        int boardScore = 0;

        for(ChessPiece piece : gameBoard.getAllPieces()){
            if(piece.getColor() == PieceColor.WHITE){
                boardScore += getPieceValue(piece);
            }else {
                boardScore -= getPieceValue(piece);
            }
        }
        return boardScore;
    }

    private static int getPieceValue(ChessPiece piece){

        if(piece == null){
            return 0;
        }

        switch (piece.getType()){
            case KING -> {return KING_VALUE;}
            case QUEEN -> {return QUEEN_VALUE;}
            case BISHOP -> {return BISHOP_VALUE;}
            case ROOK -> {return ROOK_VALUE;}
            case KNIGHT -> {return KNIGHT_VALUE;}
            default -> {return PAWN_VALUE;}
        }
    }

    private static void orderMoves(List<Move> moveList, GameBoard gameBoard){
        int counter = 0;

        for(int i = 0; i < moveList.size(); ++i){
            Move move = moveList.get(i);
            if(move.getIsCapture() && gameBoard.getPieceAtSquare(move.getFrom()).getType() == PieceType.PAWN){
                switchMoves(moveList, counter, i);
                ++counter;

            }else if(move.getIsPromotion()){
                switchMoves(moveList, counter, i);
                ++counter;

            }

        }

    }

    private static void switchMoves(List<Move> moveList, int index1, int index2){
        Move temp = moveList.get(index1);
        moveList.set(index1, moveList.get(index2));
        moveList.set(index2, temp);

    }

    @Override
    protected Move doInBackground() throws Exception {
        return getBestMove();
    }

    @Override
    protected void done() {
        try{
            Move AIMove = get();

            game.makeMove(AIMove);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
