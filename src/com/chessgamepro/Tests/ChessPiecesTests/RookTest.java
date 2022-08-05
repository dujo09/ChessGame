package com.chessgamepro.Tests.ChessPiecesTests;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;
import com.chessgamepro.GameBoard.MoveHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @DisplayName("Pseudo-legal move generation for Rook should work")
    @Test
    void TestRookPseudoLegalMoves(){

        GameBoard gameBoard = new GameBoard("rnbqkbnr/pp1pp1pp/8/2p2R2/7P/8/PPPPPPP1/RNBQKBN1");
        ChessPiece rook = gameBoard.getPieceAtSquare(new Coordinate(4,5));

        List<Move> expectedMoveList = new ArrayList<>();

        Coordinate to = rook.getPosition().getCopy();
        to.increaseRow(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy(), gameBoard.getPieceAtSquare(to)));

        to = rook.getPosition().getCopy();
        to.increaseColumn(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.increaseColumn(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));

        to = rook.getPosition().getCopy();
        to.decreaseRow(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.decreaseRow(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));

        to = rook.getPosition().getCopy();
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy()));
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(rook.getPosition().getCopy(), to.getCopy(), gameBoard.getPieceAtSquare(to)));

        List<Move> generatedMoveList = MoveHelper.getPieceMoves(rook, gameBoard, false, false);

        assertAll(
                () -> assertTrue(generatedMoveList.containsAll(expectedMoveList)),
                () -> assertEquals(expectedMoveList.size(), generatedMoveList.size())
        );
    }

}