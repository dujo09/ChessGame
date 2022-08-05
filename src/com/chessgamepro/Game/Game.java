package com.chessgamepro.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.chessgamepro.ChessAI.ChessAI;
import com.chessgamepro.ChessPieces.*;
import com.chessgamepro.GUI.GamePanel;
import com.chessgamepro.GUI.Screen;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;
import com.chessgamepro.GameBoard.MoveHelper;

import javax.swing.*;


/**
 * Class containing different methods for drawing images
 * related to the chess game
 */
public class Game {
    private final GamePanel gamePanel;

    private final GameBoard gameBoard;
    private ChessPiece selectedPiece;
    private final List<Move> selectedPieceLegalMoves;
    private PieceColor colorPlaying;
    private List<Move> colorPlayingLegalMoves;
    private final ChessPiece whiteKing;
    private final ChessPiece blackKing;
    private boolean isKingInCheck;
    private boolean isWhiteAI;
    private boolean isBlackAI;
    private boolean isTimeControlled;

    /**
     * Constructor for Game
     */
    public Game(String fen, GamePanel gamePanel){

        this.gamePanel = gamePanel;

        gameBoard = new GameBoard(fen);

        whiteKing = gameBoard.getPiece(piece ->
                piece.getType() == PieceType.KING &&
                piece.getColor() == PieceColor.WHITE
        );
        blackKing = gameBoard.getPiece(piece ->
                piece.getType() == PieceType.KING &&
                piece.getColor() == PieceColor.BLACK
        );

        colorPlaying = PieceColor.WHITE;

        selectedPiece = null;
        selectedPieceLegalMoves = new ArrayList<>();
        colorPlayingLegalMoves =  new ArrayList<>();

        isKingInCheck = false;

        isWhiteAI = false;
        isBlackAI = false;

        isTimeControlled = false;
    }

    /**
     * Method that instantiates the legal moves for color
     * playing first and makes the AI move if AI is first
     * to move
     */
    public void instantiateGame(){
        colorPlayingLegalMoves = MoveHelper.getColorMoves(
                colorPlaying,
                gameBoard,
                true,
                isColorPlayingAI()
        );

        if(isColorPlayingAI()){
            ChessAI chessAI = new ChessAI(
                    this,
                    colorPlayingLegalMoves,
                    colorPlaying == PieceColor.WHITE,
                    3
            );

            chessAI.execute();

        }

    }

    /**
     * Method that draws all images to the board panel
     *
     * @param g the graphics object
     * @param y vertical component of coordinate
     *          of the mouse cursor in device space
     * @param x horizontal component of coordinate
     *          of the mouse cursor in device space
     */
    public void drawAllBoard(Graphics g, int y, int x){
        // Draw game board to screen
        drawBoard(g);
        // Draw last made move
        drawLastMove(g);
        // Draw King in check
        drawKingInCheck(g);
        // Draw all pieces to screen
        drawPieces(g);
        // Draw highlight over square from which piece is dragging
        highlightSquareDraggingFrom(g);
        // Draw all moves that a piece can make legally
        drawLegalMoves(g);
        // Draw dragging piece if there is one
        drawDraggingPiece(g, y, x);
    }

    /* Block of 'drawAllBoard' helper methods */

    /**
     * Helper method for drawAllBoard method that draws the
     * chess board
     *
     * @param g the graphics object
     */
    private void drawBoard(Graphics g){

        for(int i = 0; i < GameBoard.ROW_COUNT; ++i){
            for(int j = 0; j < GameBoard.COLUM_COUNT; ++j){

                if((i+j) % 2 == 0){
                    g.setColor(Screen.DARK_COLOR);
                }else{
                    g.setColor(Screen.BRIGHT_COLOR);
                }

                g.fillRect(
                        getXFromColumn(j),
                        getYFromRow(i),
                        gamePanel.getSquareSize(),
                        gamePanel.getSquareSize()
                );
            }
        }
    }

    /**
     * Helper method for 'drawAllBoard' method that draws all pieces
     * at their respective positions on the board
     *
     * @param g the graphics object
     */
    private void drawPieces(Graphics g){

        for (ChessPiece piece : gameBoard.getAllPieces()) {

            if(piece.equals(selectedPiece)){
                continue;
            }

            g.drawImage(
                    piece.getPieceImage(),
                    getXFromColumn(piece.getPosition().getColumn()),
                    getYFromRow(piece.getPosition().getRow()),
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize(),
                    null
            );
        }
    }

    /**
     * Helper method for 'drawAllBoard' method that highlights the square from
     * which the selected piece is being dragged
     *
     * @param g the graphics object
     */
    private void highlightSquareDraggingFrom(Graphics g){

        if(isPieceSelected()){

            int y = getYFromRow(selectedPiece.getPosition().getRow());
            int x = getXFromColumn(selectedPiece.getPosition().getColumn());

            g.setColor(Screen.HIGHLIGHTED_SQUARE_COLOR);
            g.fillRect(
                    x,
                    y,
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize());
        }
    }

    /**
     * Helper method for 'drawAllBoard' method that draws all the legal moves
     * that the selected piece can make
     *
     * @param g the graphics object
     */
    private void drawLegalMoves(Graphics g) {

        if (isPieceSelected()) {

            for (Move move : getSelectedPieceLegalMoves()) {

                if (move.getIsCapture()) {
                    g.setColor(Screen.CAPTURE_MOVE_COLOR);
                } else {
                    g.setColor(Screen.LEGAL_MOVE_COLOR);
                }

                g.fillOval(
                        getXFromColumn(move.getTo().getColumn()) +
                                gamePanel.getSquareSize() / 3 + gamePanel.getSquareSize() / 20,
                        getYFromRow(move.getTo().getRow()) +
                                gamePanel.getSquareSize() / 3 + gamePanel.getSquareSize() / 20,
                        gamePanel.getSquareSize()/4,
                        gamePanel.getSquareSize()/4
                );
            }
        }
    }

    /**
     * Helper method for 'drawAllBoard' method that draws a red rectangle around
     * a king that is in check
     *
     * @param g the graphics object
     */
    private void drawKingInCheck(Graphics g) {

        if (isKingInCheck) {

            ChessPiece king = colorPlaying == PieceColor.WHITE ? whiteKing : blackKing;

            Graphics2D g2D = (Graphics2D) g;

            Stroke oldStroke = g2D.getStroke();

            g2D.setStroke(new BasicStroke(3));
            g2D.setColor(Color.RED.brighter());
            g2D.drawRect(
                    getXFromColumn(king.getPosition().getColumn()),
                    getYFromRow(king.getPosition().getRow()),
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize()
            );

            g2D.setStroke(oldStroke);

        }
    }

    /**
     * Helper method for 'drawAllBoard' method that draws the
     * last made move
     *
     * @param g the graphics object
     */
    private void drawLastMove(Graphics g){

        Move lastMove = gameBoard.getLastMove();

        if(lastMove != null){

            g.setColor(Screen.HIGHLIGHTED_SQUARE_COLOR);

            g.fillRect(
                    getXFromColumn(lastMove.getFrom().getColumn()),
                    getYFromRow(lastMove.getFrom().getRow()),
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize()
            );

            g.fillRect(
                    getXFromColumn(lastMove.getTo().getColumn()),
                    getYFromRow(lastMove.getTo().getRow()),
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize()
            );
        }
    }

    /**
     * Helper method for 'drawAllBoard' method that draws a piece at
     * cursor position if a piece is being dragged
     *
     * @param g the graphics object
     * @param y vertical component of coordinate of the mouse cursor
     *          in device space
     * @param x horizontal component of coordinate of the mouse cursor
     *          in device space
     */
    private void drawDraggingPiece(Graphics g, int y, int x){

        if(isPieceSelected()){

            ChessPiece selectedPiece = getSelectedPiece();

            g.drawImage(
                    selectedPiece.getPieceImage(),
                    x - gamePanel.getSquareSize()/2,
                    y - gamePanel.getSquareSize()/2,
                    gamePanel.getSquareSize(),
                    gamePanel.getSquareSize(),
                    null
            );
        }
    }

    /* End of block of 'drawAllBoard' helper methods */

    /**
     * Method that calculates where to draw the captured pieces on the panel
     * given the current panel width and height
     *
     * @param g the Graphics object
     * @param capturedPiecesColor the color of captured pieces drawing
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     */
    public void drawCapturedPieces(Graphics g, PieceColor capturedPiecesColor,
                                   int panelWidth, int panelHeight){


        List<ChessPiece> capturedPieces = capturedPiecesColor == PieceColor.WHITE ?
                gameBoard.getCapturedWhitePieces() : gameBoard.getCapturedBlackPieces();

        int piecesInRowCounter = 0;
        int y = 0;
        int x = 0;

        boolean toFitInOneRow = panelHeight < panelWidth/3;

        for(ChessPiece capturedWhitePiece : capturedPieces) {

            int pieceSize = toFitInOneRow ? panelWidth / 8: panelHeight / 2;

            g.drawImage(capturedWhitePiece.getPieceImage(),
                    x + pieceSize / 2 * piecesInRowCounter + panelWidth / 26,
                    y,
                    pieceSize,
                    pieceSize,
                    null);

            ++piecesInRowCounter;
            if (piecesInRowCounter == 5 && !toFitInOneRow) {
                y += panelHeight/4;
                piecesInRowCounter = 0;
            }
        }

    }

    /**
     * Method that displays a question message to ask the user
     * what to promote a piece into
     *
     * @return the user-selected piece type
     */
    private PieceType getPromotionTypeFromUser(){

        int typeChoice = JOptionPane.showOptionDialog(
                null,
                "Choose promotion",
                "Promote piece",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                selectedPiece.getPromotions(),
                0
        );

        return selectedPiece.getPromotions()[typeChoice];
    }

    /**
     * Method that selects a piece on the board
     *
     * @param y vertical component of coordinate of mouse cursor
     *          in device space
     * @param x horizontal component of coordinate of mouse cursor
     *          in device space
     */
    public void selectPiece(int y, int x){

        Coordinate selectedSquare = new Coordinate(getRowFromY(y), getColumnFromX(x));

        if(isPieceValidForSelection(y,x)){
            selectedPiece = gameBoard.getPieceAtSquare(selectedSquare);
            setSelectedPieceLegalMoves();
        }
    }

    /**
     * Method that checks if the piece user is hovering over is valid for selection
     *
     * @param y vertical component of coordinate in device space
     * @param x horizontal component of coordinate in device space
     * @return true if piece is valid for selection
     */
    public boolean isPieceValidForSelection(int y, int x){
        Coordinate square = new Coordinate(getRowFromY(y), getColumnFromX(x));

        return gameBoard.isColorAtSquare(square, colorPlaying) &&
                !isColorPlayingAI();

    }

    /**
     * Method that sets current selected piece legal moves
     */
    private void setSelectedPieceLegalMoves(){

        // Go through all legal moves of currently playing color and look
        // for the ones that have the same 'from' coordinate as the position
        // of the piece
        for(Move move : colorPlayingLegalMoves){

            if(move.getFrom().equals(selectedPiece.getPosition())){
                selectedPieceLegalMoves.add(move);
            }
        }
        }

    /**
     * Method that moves the piece selected by the player
     *
     * @param y vertical component of the coordinate
     *          in device space of the square to move to
     * @param x horizontal component of the coordinate
     *          in device space of the square to move to
     */
    public void moveSelectedPiece(int y, int x){

        Coordinate to = new Coordinate(getRowFromY(y), getColumnFromX(x));

        // Go through all selected piece legal moves and look for the one that
        // has the same 'to' coordinate as the move the player is trying to make
        for(Move move : selectedPieceLegalMoves){
            if(move.getTo().equals(to)){

                if(move.getIsPromotion()){
                    move.setPromotedPieceType(getPromotionTypeFromUser());
                }

                makeMove(move);

                break;
            }
        }

        selectedPiece = null;
        selectedPieceLegalMoves.clear();
    }

    public void makeMove(Move move){
        gameBoard.makeMove(move);

        gamePanel.revalidate();
        gamePanel.repaint();

        switchSide();

    }

    /**
     * Method that switches the currently playing color and
     * checks if it is check-mate or stale-mate
     */
    public void switchSide(){
        colorPlaying = colorPlaying.getOppositeColor();

        gamePanel.setColorPlayingPanel(colorPlaying);
        if(isTimeControlled){
            gamePanel.setTimerPanels(colorPlaying);
        }

        colorPlayingLegalMoves = MoveHelper.getColorMoves(colorPlaying, gameBoard,
                true,isColorPlayingAI());

        // Get the color playing King and check if check-mate or stale-mate
        ChessPiece king = colorPlaying == PieceColor.WHITE ? whiteKing : blackKing;
        isKingInCheck = gameBoard.areSquaresInCheck(colorPlaying.getOppositeColor(), king.getPosition());

        if(colorPlayingLegalMoves.size() == 0){
            if(isKingInCheck){
                JOptionPane.showMessageDialog(
                        null,
                        "CHECKMATE FOR " + colorPlaying.getOppositeColor()
                );
            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "STALEMATE"
                );
            }
            System.exit(0);
        }

        if(isColorPlayingAI()){
            ChessAI chessAI = new ChessAI(
                    this,
                    colorPlayingLegalMoves,
                    colorPlaying == PieceColor.WHITE,
                    3
            );

            chessAI.execute();

        }
    }

    /**
     * Method that ends the game on the basis that a color ran
     * out of time, called by the timer when it reaches zero
     */
    public void endGameOutOfTime(){

        JOptionPane.showMessageDialog(
                null,
                colorPlaying + " OUT OF TIME, " +
                        colorPlaying.getOppositeColor() + " WINS"
        );
        System.exit(0);
    }

    /**
     * Method that checks if the color playing is AI
     *
     * @return true if AI is playing now, false if human is playing now
     */
    public boolean isColorPlayingAI(){
        return (isWhiteAI && colorPlaying == PieceColor.WHITE) ||
                (isBlackAI && colorPlaying == PieceColor.BLACK);
    }

    /**
     * Method that checks if AI is in game at all
     */
    public boolean isAIInGame(){
        return isWhiteAI || isBlackAI;
    }

    /**
     * Method for checking if a piece is selected
     *
     * @return true a piece is selected and false if no piece is selected
     */
    public boolean isPieceSelected(){
        return selectedPiece != null;
    }

    /**
     * Method that gets the current selected piece
     *
     * @return the ChessPiece currently selected
     */
    public ChessPiece getSelectedPiece(){
        return selectedPiece;
    }

    /**
     * Method that returns the game board associated with
     * this game
     *
     * @return the game board
     */
    public GameBoard getGameBoard(){
        return gameBoard;

    }

    /**
     * Method that gets all the legal moves of the selected piece
     *
     * @return a List of moves the selected piece can make
     */
    public List<Move> getSelectedPieceLegalMoves(){
        return selectedPieceLegalMoves;
    }

    public void setIsTimeControlled(boolean isTimeControlled){
        this.isTimeControlled = isTimeControlled;

    }

    public void setIsWhiteAI(boolean isWhiteAI){
        this.isWhiteAI = isWhiteAI;

    }

    public void setIsBlackAI(boolean isBlackAI){
        this.isBlackAI = isBlackAI;

    }

    /* Block of methods for converting between screen coordinates and board coordinates */

    /**
     * Method that calculates the row of a square from device space
     * coordinate, serves as an in-between method
     *
     * @param y vertical component of a coordinate in device space
     * @return row value
     */
    public int getRowFromY(int y){
        return GameBoard.ROW_COUNT - 1 + (gamePanel.getBoardTopLeftY() - y) / gamePanel.getSquareSize();
    }

    /**
     * Method that calculates the column of a square from device space
     * coordinate, serves as an in-between method
     *
     * @param x horizontal component of a coordinate in device space
     * @return column value
     */
    public int getColumnFromX(int x){
        return (x - gamePanel.getBoardTopLeftX()) / gamePanel.getSquareSize();
    }

    /**
     * Method that calculates the device space coordinate of a square
     * on chess board, serves as an in-between method
     *
     * @param row row of square on chess board
     * @return vertical component of the coordinate in device space
     */
    public int getYFromRow(int row){
        return gamePanel.getSquareSize()*(GameBoard.ROW_COUNT - 1 - row) + gamePanel.getBoardTopLeftY();
    }

    /**
     * Method that calculates the device space coordinate of a square
     * on chess board, serves as an in-between method
     *
     * @param column column of square on chess board
     * @return vertical component of the coordinate in device space
     */
    public int getXFromColumn(int column){
        return column * gamePanel.getSquareSize() + gamePanel.getBoardTopLeftX();
    }

    /* End of block of methods for converting between screen coordinates and board coordinates */
}
