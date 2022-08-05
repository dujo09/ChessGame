package com.chessgamepro.Tests;

import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.FenUtility.FenUtility;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;
import com.chessgamepro.GameBoard.MoveHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveHelperTest {

    public static int generateMovesIteratively(int depth, PieceColor color, GameBoard gameBoard){

        if(depth == 0){
            return 1;
        }

        List<Move> moveList = MoveHelper.getColorMoves(color, gameBoard, true,false);
        int positionsCount = 0;

        for(Move move : moveList){
            gameBoard.makeMove(move);
            //gameBoard.printToScreen();
            positionsCount += generateMovesIteratively(depth-1, color.getOppositeColor(), gameBoard.getCopy());
            gameBoard.undoMove();
        }

        return positionsCount;
    }

    @Test
    @DisplayName("Positions visited through recursive move generation " +
            "should align with what other programmers got for 'start' fen")
    void TestMoveGenerationStartFen(){
        GameBoard gameBoard = new GameBoard(FenUtility.startFen);

       /* System.out.println("Positions evaluated " +
                generateMovesIteratively(5, PieceColor.WHITE, gameBoard));*/

       assertAll("Move generation from typical chess start should work",
                () -> assertEquals(20, generateMovesIteratively(1,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 20 positions with depth of 1"),
                () -> assertEquals(400, generateMovesIteratively(2,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 400 positions with depth of 2"),
                () -> assertEquals(8902, generateMovesIteratively(3,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 8902 positions with depth of 3"),
                () -> assertEquals(197281, generateMovesIteratively(4,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 197281 positions with depth of 4"),
                () -> assertEquals(4865609, generateMovesIteratively(5,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 4865609 positions with depth of 5"),
                () -> assertEquals(119060324, generateMovesIteratively(6,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 119060324 positions with depth of 6")
        );
    }

    @Test
    @DisplayName("Positions visited through recursive move generation " +
            "should align with what other programmers got for 'position 2' fen")
    void TestMoveGenerationPosition2(){
        GameBoard gameBoard = new GameBoard(FenUtility.position2);

       /* System.out.println("Positions evaluated " +
                generateMovesIteratively(5, PieceColor.WHITE, gameBoard));*/

        assertAll("Move generation from typical chess start should work",
                () -> assertEquals(48, generateMovesIteratively(1,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 20 positions with depth of 1"),
                () -> assertEquals(2039, generateMovesIteratively(2,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 400 positions with depth of 2"),
                () -> assertEquals(97862, generateMovesIteratively(3,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 8902 positions with depth of 3"),
                () -> assertEquals(4085603, generateMovesIteratively(4,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 197281 positions with depth of 4"),
                () -> assertEquals(193690690, generateMovesIteratively(5,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 4865609 positions with depth of 5"),
                () -> assertEquals(1, generateMovesIteratively(6,PieceColor.WHITE, gameBoard),
                        "Expecting to visit 119060324 positions with depth of 6")
        );
    }


}