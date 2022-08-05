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

class BishopTest {

    @DisplayName("Pseudo-legal move generation for Bishop should work")
    @Test
    void TestBishopPseudoLegalMoves(){

        GameBoard gameBoard = new GameBoard("rnbqkbnr/pp1ppppp/2p5/8/3P1B2/4P3/PPP2PPP/RN1QKBNR");
        ChessPiece bishop = gameBoard.getPieceAtSquare(new Coordinate(3,5));

        List<Move> expectedMoveList = new ArrayList<>();

        Coordinate to = bishop.getPosition().getCopy();
        to.increaseRow(1);
        to.increaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        to.increaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));

        to = bishop.getPosition().getCopy();
        to.decreaseRow(1);
        to.increaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));

        to = bishop.getPosition().getCopy();
        to.increaseRow(1);
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy()));
        to.increaseRow(1);
        to.decreaseColumn(1);
        expectedMoveList.add(new Move(bishop.getPosition().getCopy(), to.getCopy(),
                gameBoard.getPieceAtSquare(to)));

        List<Move> generatedMoveList = MoveHelper.getPieceMoves(bishop, gameBoard, false, false);

        assertAll(
                () -> assertTrue(generatedMoveList.containsAll(expectedMoveList)),
                () -> assertEquals(expectedMoveList.size(), generatedMoveList.size())
        );
    }

}