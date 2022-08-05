package com.chessgamepro.GameBoard;

import com.chessgamepro.ChessPieces.*;
import com.chessgamepro.FenUtility.FenCharacter;
import com.chessgamepro.FenUtility.FenData;
import com.chessgamepro.FenUtility.FenUtility;

import java.util.*;

public class GameBoard {

    // Constants describing the size of the image of game board
    public static final int HEIGHT = 640;
    public static final int WIDTH = 640;

    //Constants describing size of board
    public static final int COLUM_COUNT = 8;
    public static final int ROW_COUNT = 8;

    private final ChessPiece[][] boardLayout;
    private final List<ChessPiece> allPieces;
    private final Stack<Move> lastMoves;

    // Keep track of captured pieces, only used for displaying
    private final List<ChessPiece> capturedWhitePieces;
    private final List<ChessPiece> capturedBlackPieces;

    /**
     * Constructor for GameBoard
     *
     * @param fen the fen string holding data for board construction
     */
    public GameBoard(String fen){

        FenData decodedFenData = FenUtility.getDataFromFen(fen);

        this.allPieces = decodedFenData.allPieces();
        this.boardLayout = decodedFenData.boardLayout();

        capturedWhitePieces = new ArrayList<>();
        capturedBlackPieces = new ArrayList<>();

        lastMoves = new Stack<>();
    }

    /**
     * Copy constructor for GameBoard
     *
     * @param gameBoard the reference GameBoard object by which we are
     *                  creating a new one
     */
    public GameBoard(GameBoard gameBoard){

        this.allPieces = new ArrayList<>();
        this.capturedWhitePieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.boardLayout = new ChessPiece[ROW_COUNT][COLUM_COUNT];

        for(ChessPiece piece : gameBoard.allPieces){
            ChessPiece pieceCopy = piece.getCopy();

            this.allPieces.add(pieceCopy);
            this.boardLayout[pieceCopy.getPosition().getRow()][pieceCopy.getPosition().getColumn()] =
                    pieceCopy;
        }

        this.lastMoves = new Stack<>();

        if(gameBoard.lastMoves != null){

            Stack<Move> intermediateStack = new Stack<>();

            intermediateStack.addAll(gameBoard.lastMoves);

            this.lastMoves.addAll(intermediateStack);
        }
    }

    /**
     * Overridden method that checks if a given GameBoard is equal to
     * this instance of GameBoard
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof GameBoard otherGameBoard)) {
            return false;
        }

        for(ChessPiece piece : allPieces){
            if(!otherGameBoard.allPieces.contains(piece)){
                return false;
            }
        }

        if(allPieces.size() != otherGameBoard.allPieces.size()){
            return false;
        }

        return true;
    }

    /**
     * Method for moving a piece to a square on the board
     *
     * @param move move being made
     */
    public void makeMove(Move move){

        Coordinate from = move.getFrom();
        Coordinate to = move.getTo();
        ChessPiece pieceMoving = getPieceAtSquare(from);

        if(move.getIsCapture()){
            for(ChessPiece capturedPiece : move.getCapturedPieces()){

                allPieces.remove(capturedPiece);
                boardLayout[capturedPiece.getPosition().getRow()][capturedPiece.getPosition().getColumn()] = null;

                if(capturedPiece.getColor() == PieceColor.WHITE){
                    capturedWhitePieces.add(capturedPiece);
                }else{
                    capturedBlackPieces.add(capturedPiece);
                }
            }
        }

        if(move.getIsMoveTrigger()){
            for(Move triggeredMove : move.getTriggeredMoves()){
                makeMove(triggeredMove);
            }
        }

        if(move.getIsPromotion() && move.getPromotedPieceType() != null){
            allPieces.remove(pieceMoving);
            pieceMoving = pieceMoving.getPromotedPiece(move.getPromotedPieceType());
            allPieces.add(pieceMoving);
        }

        // Make the move
        boardLayout[to.getRow()][to.getColumn()] = pieceMoving;
        boardLayout[from.getRow()][from.getColumn()] = null;
        pieceMoving.setPosition(to.getCopy());

        // If piece has not moved yet then this the first move for this piece
        if(!pieceMoving.getHasMoved()){
            move.setIsFirstMove(true);
        }
        pieceMoving.setHasMoved(true);

        // Update stack lastMoves, keep track of up to 3 moves
        lastMoves.push(move);
        if(lastMoves.size() > 3){
            lastMoves.remove(0);
        }
    }

    /**
     * Method that undoes the last made move
     */
    public void undoMove(){

        if(lastMoves.size() == 0){
            return;
        }

        Move move = lastMoves.pop();

        // Reversed to and from coordinates
        Coordinate from = move.getTo();
        Coordinate to = move.getFrom();
        ChessPiece pieceMoving = getPieceAtSquare(from);

        // If move being undone is first move of piece then update the hasMoved to false
        if(move.getIsFirstMove()){
            pieceMoving.setHasMoved(false);
        }

        // First undo all moves that were triggered by this move
        int triggeredMovesCount = move.getTriggeredMoves().size();
        for(int i = 0; i < triggeredMovesCount; ++i){
            undoMove();
        }

        // Make the reverse move
        boardLayout[to.getRow()][to.getColumn()] = pieceMoving;
        boardLayout[from.getRow()][from.getColumn()] = null;
        pieceMoving.setPosition(to.getCopy());

        // 'Bring back' all pieces that were captured
        if(move.getIsCapture()){
            for(ChessPiece capturedPiece : move.getCapturedPieces()){
                allPieces.add(capturedPiece);
                boardLayout[capturedPiece.getPosition().getRow()][capturedPiece.getPosition().getColumn()] =
                        capturedPiece;
            }
        }
    }

    /**
     * Method gets the piece at a given square
     *
     * @param coordinate the given coordinate of the square
     * @return the chess piece at given coordinate, null if no
     * piece at coordinate
     */
    public ChessPiece getPieceAtSquare(Coordinate coordinate){

        if(isSquareOutOfBounds(coordinate)){
            return null;
        }

        return boardLayout[coordinate.getRow()][coordinate.getColumn()];
    }

    /**
     * Method that checks if a piece of given color is at a square
     *
     * @param coordinate the given coordinate of the square
     * @param color the color checking for
     * @return true if a piece of specified color is at square, false otherwise
     */
    public boolean isColorAtSquare(Coordinate coordinate, PieceColor color){

        if(isSquareOutOfBounds(coordinate) || isSquareEmpty(coordinate)){
            return false;
        }

        return getPieceAtSquare(coordinate).getColor() == color;
    }

    /**
     * Method that checks if a piece of given type is at a square
     *
     * @param coordinate the given coordinate of the square
     * @param type the type checking for
     * @return true if a piece of specified type is at a square, false otherwise
     */
    public boolean isTypeAtSquare(Coordinate coordinate, PieceType type){

        if(isSquareOutOfBounds(coordinate) || isSquareEmpty(coordinate)){
            return false;
        }

        return getPieceAtSquare(coordinate).getType() == type;
    }

    /**
     * Method that checks if no piece is at the given square
     *
     * @param coordinate the given coordinate of the square
     * @return true if empty, false otherwise
     */
    public boolean isSquareEmpty(Coordinate coordinate){
        return getPieceAtSquare(coordinate) == null;
    }

    /**
     * Method that checks if the given square is out of bounds of the board
     *
     * @param coordinate the given coordinate of the square
     * @return true if coordinate is out of bounds, false otherwise
     */
    public boolean isSquareOutOfBounds(Coordinate coordinate){
        return coordinate.getRow() >= ROW_COUNT || coordinate.getRow() < 0 ||
                coordinate.getColumn() >= COLUM_COUNT || coordinate.getColumn() < 0;
    }

    /**
     * Method that checks if any square in given squares is in check by
     * the opponent
     *
     * @param squares the squares checking for
     * @param opponentColor the color of the opponent
     * @return true if any of the squares are in check and false if none of them
     * are in check
     */
    public boolean areSquaresInCheck(PieceColor opponentColor, Coordinate...squares){

        List<Move> opponentMoves = MoveHelper.getColorMoves(opponentColor, this,
                false, true);

        for(Move opponentMove : opponentMoves){

            for(Coordinate square : squares){

                if(opponentMove.getTo().equals(square)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method for getting last move made on board
     */
    public Move getLastMove(){
        if(lastMoves.size() == 0){
            return null;
        }

        return lastMoves.peek();
    }

    /**
     * Method that returns all the pieces on board
     *
     * @return a list of all pieces
     */
    public List<ChessPiece> getAllPieces(){
        return allPieces;
    }

    // Methods for getting the captured pieces lists
    public List<ChessPiece> getCapturedWhitePieces(){
        return capturedWhitePieces;
    }
    public List<ChessPiece> getCapturedBlackPieces(){
        return capturedBlackPieces;
    }

    /**
     * Method for getting a single piece that satisfy a condition
     *
     * @param checker interface by which a condition is set
     * @return a single piece
     */
    public ChessPiece getPiece(CheckPiece checker){

        for(ChessPiece piece : allPieces){

            if(checker.test(piece)){
                return piece;
            }
        }
        return null;
    }

    /**
     * Method that gets a copy of this GameBoard instance
     * by calling the GameBoard copy constructor on this
     * GameBoard instance
     *
     * @return an exact copy of this GameBoard
     */
    public GameBoard getCopy(){

        return new GameBoard(this);
    }

    /**
     * Method used for debugging that prints the game board to the console
     */
    public void printToScreen(){

        System.out.println();

        for(int i = ROW_COUNT-1; i >= 0; --i){

            System.out.print((i + 1) + "  ");

            for(int j = 0; j < COLUM_COUNT; ++j){
                if(lastMoves.size() > 0 &&
                lastMoves.peek().getFrom().getRow() == i &&
                lastMoves.peek().getFrom().getColumn() == j){
                    System.out.print("#  ");
                }else if(boardLayout[i][j] == null){
                    System.out.print("-  ");
                }else{
                    System.out.printf("%c  ", FenCharacter.getSymbol(boardLayout[i][j]));
                }
            }
            System.out.println();
        }

        System.out.print("   ");
        for(char i = 'a'; i < 'i'; ++i){
            System.out.print(i + "  ");
        }

        System.out.println();
    }
}
