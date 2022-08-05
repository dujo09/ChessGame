package com.chessgamepro.Tests;

import com.chessgamepro.ChessPieces.*;
import com.chessgamepro.FenUtility.FenUtility;
import com.chessgamepro.Game.Game;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @DisplayName("Simple piece movement and capture should work")
    @Test
    void TestMakeMove() {

        GameBoard gameBoard = new GameBoard(FenUtility.startFen);

        Move pawnMove = new Move(
                new Coordinate(1,0),
                new Coordinate(2,0)
        );
        gameBoard.makeMove(pawnMove);

        assertAll("Pawn move",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(2,0), PieceType.PAWN),
                        "White Pawn moved, type at square moved to should be Pawn"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(2,0), PieceColor.WHITE),
                        "White Pawn moved, color at square moved to should be White"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(1,0)),
                        "White Pawn moved, square from which he moved should be empty")
        );

        Move bishopMove1 = new Move(
                new Coordinate(0, 0),
                new Coordinate(2, 0)
        );
        gameBoard.makeMove(bishopMove1);

        Move bishopMove2 = new Move(
                new Coordinate(2,0),
                new Coordinate(2,3)
        );
        gameBoard.makeMove(bishopMove2);

        Move bishopCapture = new Move(
                new Coordinate(2, 3),
                new Coordinate(6, 3),
                gameBoard.getPieceAtSquare(new Coordinate(6,3))
        );
        gameBoard.makeMove(bishopCapture);

        assertAll("White capture",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(6,3), PieceType.ROOK),
                        "White Rook captured, type at square moved to should be Rook"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(6,3), PieceColor.WHITE),
                        "White Rook captured, color at square moved to should be White"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(0,0)),
                        "White Rook captured, square from which he moved should be empty"),
                () -> assertFalse(gameBoard.getAllPieces().contains(bishopCapture.getCapturedPieces()),
                        "White Rook captured, captured piece should not be a piece on the board")
        );

        Move queenCapture = new Move(
                new Coordinate(7,3),
                new Coordinate(6,3),
                gameBoard.getPieceAtSquare(new Coordinate(6,3))
        );
        gameBoard.makeMove(queenCapture);

        assertAll("Black capture",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(6,3), PieceType.QUEEN),
                        "Black Queen captured, type at square moved to should be Queen"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(6,3), PieceColor.BLACK),
                        "Black Queen captured, color at square moved to should be Black"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(7,3)),
                        "Black Queen captured, square from which he moved should be empty"),
                () -> assertFalse(gameBoard.getAllPieces().contains(bishopCapture.getCapturedPieces()),
                        "Black Queen captured, captured piece should not be a piece on the board")
        );
    }

    @DisplayName("Special Pawn movement and capture should work")
    @Test
    void TestMakePawnSpecialMove(){

        GameBoard gameBoard = new GameBoard("rnbqkbnr/pppp1ppp/8/8/4p3/8/PPPPPPPP/RNBQKBNR");

        Move pawnTwoForwardMove = new Move(
                new Coordinate(1,3),
                new Coordinate(3,3)
        );

        gameBoard.makeMove(pawnTwoForwardMove);

        assertAll("White Pawn two-forward move",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(3,3), PieceType.PAWN),
                        "White Pawn moved, type at square moved to should be Pawn"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(3,3), PieceColor.WHITE),
                        "White Pawn moved, color at square moved to should be White"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(1,3)),
                        "White Pawn just moved, square from which he moved should be empty")
        );

        Move pawnEnPassantCapture = new Move(
                new Coordinate(3,4),
                new Coordinate(2,3),
                gameBoard.getPieceAtSquare(new Coordinate(3,3))
        );

        gameBoard.makeMove(pawnEnPassantCapture);

        assertAll("Black Pawn en-passant",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(2,3), PieceType.PAWN),
                        "Black Pawn captured en-passant, type at square moving to should be Pawn"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(2,3), PieceColor.BLACK),
                        "Black Pawn captured en-passant, color at square moving to should be Black"),
                () -> assertFalse(gameBoard.getAllPieces().contains(pawnEnPassantCapture.getCapturedPieces()),
                        "Black Pawn captured en-passant, captured piece should not be a piece on the board"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(3,4)),
                        "Black Pawn captured en-passant, square from which he moved should be empty")


        );
    }

    @DisplayName("Special King movement should work")
    @Test
    void TestMakeKingSpecialMove(){
        GameBoard gameBoard = new GameBoard("r3kbnr/p2ppppp/bqn5/1pp5/4PPP1/3B3N/PPPP3P/RNBQK2R");

        Move kingSideCastle = new Move(
                new Coordinate(0,4),
                new Coordinate(0,6),
                new Move(
                        new Coordinate(0,7),
                        new Coordinate(0,5)
                )
        );

        gameBoard.makeMove(kingSideCastle);

        assertAll("King-side castle",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(0,6), PieceType.KING),
                        "White King castled king-side, type at square moved to should be King"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(0,6), PieceColor.WHITE),
                        "White King castled king-side, color at square moved to should be White"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(0,4)),
                        "White King castled king-side, square from which he moved should be empty"),
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(0,5), PieceType.ROOK),
                "White King castled king-side, type at square west of King should be Rook"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(0,5), PieceColor.WHITE),
                        "White King castled king-side, color at square west of King should be White"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(0,7)),
                        "White King castled king-side, square from which Rook moved should be empty")
        );

        Move queenSideCastle = new Move(
                new Coordinate(7,4),
                new Coordinate(7,2),
                new Move(
                        new Coordinate(7,0),
                        new Coordinate(7,3)
                )
        );

        gameBoard.makeMove(queenSideCastle);

        assertAll("Queen-side castle",
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(7,2), PieceType.KING),
                        "Black King castled queen-side, type at square moved to should be King"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(7,2), PieceColor.BLACK),
                        "Black King castled queen-side, color at square moved to should be Black"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(7,4)),
                        "Black King castled queen-side, square from which he moved should be empty"),
                () -> assertTrue(gameBoard.isTypeAtSquare(new Coordinate(7,3), PieceType.ROOK),
                        "Black King castled queen-side, type at square west of King should be Rook"),
                () -> assertTrue(gameBoard.isColorAtSquare(new Coordinate(7,3), PieceColor.BLACK),
                        "Black King castled queen-side, color at square west of King should be Black"),
                () -> assertTrue(gameBoard.isSquareEmpty(new Coordinate(7,0)),
                        "Black King castled queen-side, square from which Rook moved should be empty")
        );
    }

    @DisplayName("Undoing simple moves should work")
    @Test
    void TestUndoMove() {

        GameBoard gameBoard = new GameBoard(FenUtility.startFen);

        GameBoard gameBoardCopy = gameBoard.getCopy();

        Move pawnMove1 = new Move(
                new Coordinate(6,3),
                new Coordinate(4,3)
        );
        gameBoardCopy.makeMove(pawnMove1);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard, gameBoardCopy, "Black Pawn move should be undone");
        gameBoardCopy.makeMove(pawnMove1);
        gameBoard.makeMove(pawnMove1);

        Move pawnMove2 = new Move(
                new Coordinate(6,2),
                new Coordinate(4,2)
        );
        gameBoardCopy.makeMove(pawnMove2);
        gameBoard.makeMove(pawnMove2);

        Move queenMove = new Move(
                new Coordinate(7,3),
                new Coordinate(4,0)
        );
        gameBoardCopy.makeMove(queenMove);
        gameBoard.makeMove(queenMove);

        Move queenCapture = new Move(
                new Coordinate(4,0),
                new Coordinate(1,3),
                gameBoardCopy.getPieceAtSquare(new Coordinate(1,3))
        );
        gameBoardCopy.makeMove(queenCapture);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard, gameBoardCopy, "Black Queen capture should be undone");
        gameBoardCopy.makeMove(queenCapture);
        gameBoard.makeMove(queenCapture);

        Move bishopCapture = new Move(
                new Coordinate(0,2),
                new Coordinate(1,3),
                gameBoardCopy.getPieceAtSquare(new Coordinate(1,3))
        );
        gameBoardCopy.makeMove(bishopCapture);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard,gameBoardCopy, "White Rook capture should be undone");
    }

    @DisplayName("Undoing special Pawn moves should work")
    @Test
    void TestUndoPawnSpecialMove(){
        GameBoard gameBoard = new GameBoard("rnbqkbnr/pppp1ppp/8/8/4p3/8/PPPPPPPP/RNBQKBNR");
        GameBoard gameBoardCopy = gameBoard.getCopy();

        Move pawnTwoForward = new Move(
                new Coordinate(1,3),
                new Coordinate(3,3)
        );

        gameBoardCopy.makeMove(pawnTwoForward);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard, gameBoardCopy, "White Pawn two-forward should be undone");

        gameBoardCopy.makeMove(pawnTwoForward);
        gameBoard.makeMove(pawnTwoForward);

        Move pawnEnPassantCapture = new Move(
                new Coordinate(3,4),
                new Coordinate(2,3),
                gameBoard.getPieceAtSquare(new Coordinate(3,3))
        );

        gameBoardCopy.makeMove(pawnEnPassantCapture);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard,gameBoardCopy, "Black Pawn en-passant should be undone");
    }

    @DisplayName("Undoing special King move should work")
    @Test
    void TestUndoKingSpecialMove(){
        GameBoard gameBoard = new GameBoard("r3kbnr/p2ppppp/bqn5/1pp5/4PPP1/3B3N/PPPP3P/RNBQK2R");
        GameBoard gameBoardCopy = gameBoard.getCopy();

        Move kingSideCastle = new Move(
                new Coordinate(0,4),
                new Coordinate(0,6),
                new Move(
                        new Coordinate(0,7),
                        new Coordinate(0,5)
                )
        );

        gameBoardCopy.makeMove(kingSideCastle);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard,gameBoardCopy, "King-side castle should be undone");

        Move queenSideCastle = new Move(
                new Coordinate(7,4),
                new Coordinate(7,2),
                new Move(
                        new Coordinate(7,0),
                        new Coordinate(7,3)
                )
        );

        gameBoardCopy.makeMove(queenSideCastle);
        gameBoardCopy.undoMove();

        assertEquals(gameBoard, gameBoardCopy, "Queen-side castle should be undone");
    }
}