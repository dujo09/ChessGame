package com.chessgamepro.GameBoard;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.ChessPieces.PieceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    private final Coordinate from;
    private final Coordinate to;
    private final int rowOffset;
    private final int columnOffset;
    private boolean isCapture;
    private final List<ChessPiece> capturedPieces;
    private boolean isMoveTrigger;
    private final List<Move> triggeredMoves;
    private boolean isFirstMove;
    private final boolean isPromotion;
    private final PieceType pieceType;
    private PieceType promotedPieceType;

    /**
     * Constructor for Move
     *
     * @param from coordinate of square from which a piece moves
     * @param to coordinate of square to which a piece moves
     */
    public Move(Coordinate from, Coordinate to){
        this.from = from;
        this.to = to;

        rowOffset = to.getRow() - from.getRow();
        columnOffset = to.getColumn() - from.getColumn();

        isCapture = false;
        isMoveTrigger = false;

        capturedPieces = new ArrayList<>();
        triggeredMoves = new ArrayList<>();

        isFirstMove = false;

        isPromotion = false;
        pieceType = null;
        promotedPieceType = null;
    }

    /**
     * Constructor of Move used for making capturing Moves
     *
     * @param from coordinate of square from which a piece moves
     * @param to coordinate of square to which a piece moves
     * @param capturedPieces piece this move captures
     */
    public Move(Coordinate from, Coordinate to, ChessPiece... capturedPieces){
        this.from = from;
        this.to = to;

        rowOffset = to.getRow() - from.getRow();
        columnOffset = to.getColumn() - from.getColumn();

        isCapture = true;
        isMoveTrigger = false;

        this.capturedPieces = Arrays.asList(capturedPieces);
        triggeredMoves = new ArrayList<>();

        isFirstMove = false;

        isPromotion = false;
        pieceType = null;
        promotedPieceType = null;
    }

    /**
     * Constructor of Move  used for making move triggering Moves
     *
     * @param from coordinate of square from which a piece moves
     * @param to coordinate of square to which a piece moves
     * @param triggeredMoves moves that this move triggers
     */
    public Move(Coordinate from, Coordinate to, Move... triggeredMoves){
        this.from = from;
        this.to = to;

        rowOffset = to.getRow() - from.getRow();
        columnOffset = to.getColumn() - from.getColumn();

        isCapture = false;
        isMoveTrigger = true;

        this.capturedPieces = new ArrayList<>();
        this.triggeredMoves = Arrays.asList(triggeredMoves);

        isFirstMove = false;

        isPromotion = false;
        pieceType = null;
        promotedPieceType = null;
    }

    /**
     * Constructor for Move used for making promoting moves
     *
     * @param from coordinate of square from which a piece moves
     * @param to coordinate of square to which a piece moves
     * @param pieceType the type of piece that is being promoted
     */
    public Move(Coordinate from, Coordinate to, PieceType pieceType){
        this.from = from;
        this.to = to;

        rowOffset = to.getRow() - from.getRow();
        columnOffset = to.getColumn() - from.getColumn();

        isCapture = false;
        isMoveTrigger = false;

        capturedPieces = new ArrayList<>();
        triggeredMoves = new ArrayList<>();

        isFirstMove = false;

        isPromotion = true;
        this.pieceType = pieceType;
        promotedPieceType = null;
    }

    /**
     * Constructor of Move used for making capturing and promoting Moves
     *
     * @param from coordinate of square from which a piece moves
     * @param to coordinate of square to which a piece moves
     * @param capturedPieces piece this move captures
     */
    public Move(Coordinate from, Coordinate to, PieceType pieceType, ChessPiece... capturedPieces){
        this.from = from;
        this.to = to;

        rowOffset = to.getRow() - from.getRow();
        columnOffset = to.getColumn() - from.getColumn();

        isCapture = true;
        isMoveTrigger = false;

        this.capturedPieces = Arrays.asList(capturedPieces);
        triggeredMoves = new ArrayList<>();

        isFirstMove = false;

        isPromotion = true;
        this.pieceType = pieceType;
        promotedPieceType = null;
    }


    /**
     * Copy constructor for Move
     *
     * @param move the reference Move by which a new one
     *             is created
     */
    public Move(Move move){
        this.from = move.from.getCopy();
        this.to = move.to.getCopy();

        this.rowOffset = move.rowOffset;
        this.columnOffset = move.columnOffset;

        this.isCapture = move.isCapture;
        this.isMoveTrigger = move.isMoveTrigger;

        this.capturedPieces = new ArrayList<>();

        for(ChessPiece capturedPiece : move.capturedPieces){
            this.capturedPieces.add(capturedPiece.getCopy());
        }

        this.triggeredMoves = new ArrayList<>();

        for(Move triggeredMove : move.triggeredMoves){
            this.triggeredMoves.add(triggeredMove.getCopy());
        }

        this.isFirstMove = move.isFirstMove;

        this.isPromotion = move.isPromotion;
        this.pieceType = move.pieceType;
        this.promotedPieceType = move.promotedPieceType;
    }

    /**
     * Overridden method that checks if a given Move is equal to
     * this instance of Move
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Move otherMove)){
            return false;
        }

        if(!from.equals(otherMove.from) || !to.equals(otherMove.to) ||
                isCapture != otherMove.isCapture ||
                isMoveTrigger != otherMove.isMoveTrigger ||
                isPromotion != otherMove.isPromotion){
            return false;
        }

        if(isCapture){
            for(ChessPiece capturedPiece : capturedPieces){
                if(!otherMove.capturedPieces.contains(capturedPiece)){
                    return false;
                }
            }

            if(capturedPieces.size() != otherMove.capturedPieces.size()){
                return false;
            }
        }

        if(isMoveTrigger){
            for(Move triggeredMove : triggeredMoves){
                if(!otherMove.triggeredMoves.contains(triggeredMove)){
                    return false;
                }
            }

            if(triggeredMoves.size() != otherMove.triggeredMoves.size()){
                return false;
            }
        }

        if(isPromotion && (promotedPieceType != otherMove.promotedPieceType)){
            return false;
        }
            return true;
    }

    // Methods for getting and setting the 'from' coordinate of Move
    public Coordinate getFrom(){
        return from;
    }
    public void setFrom(int row, int column){
        from.setRow(row);
        from.setColumn(column);
    }

    // Methods for getting and setting the 'to' coordinate of Move
    public Coordinate getTo(){
        return to;
    }
    public void setTo(int row, int column){
        to.setRow(row);
        to.setColumn(column);
    }

    // Methods for getting the row and column offset of this move
    public int getRowOffset() {
        return rowOffset;
    }
    public int getColumnOffset() {
        return columnOffset;
    }

    // Methods for getting and setting if a move results in a capture
    public boolean getIsCapture(){
        return isCapture;
    }
    public void setIsCapture(boolean newIsCapture){
        isCapture = newIsCapture;
    }

    // Methods for getting and setting capturing pieces
    public List<ChessPiece> getCapturedPieces(){
        return capturedPieces;
    }
    public void addCapturingPieces(ChessPiece...newCapturingPieces){
        capturedPieces.addAll(Arrays.asList(newCapturingPieces));
    }

    // Methods for getting and setting if a move triggers other moves
    public boolean getIsMoveTrigger(){
        return isMoveTrigger;
    }
    public void setIsMoveTrigger(boolean newIsMoveTrigger){
        isMoveTrigger = newIsMoveTrigger;
    }

    // Methods for getting and setting triggering moves
    public List<Move> getTriggeredMoves(){
        return triggeredMoves;
    }
    public void addTriggeringMoves(Move...newTriggeringMoves){
        triggeredMoves.addAll(Arrays.asList(newTriggeringMoves));
    }

    // Methods for getting and setting if this is the first of a piece
    public boolean getIsFirstMove(){
        return isFirstMove;
    }
    public void setIsFirstMove(boolean newIsFirstMove){
        isFirstMove = newIsFirstMove;
    }

    // Method for getting if this move results in a promotion
    public boolean getIsPromotion(){
        return isPromotion;
    }

    // Method for getting and setting the type of piece that this promoting move promotes into
    public PieceType getPromotedPieceType(){
        return promotedPieceType;
    }
    public void setPromotedPieceType(PieceType newPromotedPieceType){
        promotedPieceType = newPromotedPieceType;
    }

    // Method for getting the type of piece before promotion
    public PieceType getPieceType(){
        return pieceType;
    }

    /**
     * Overriding method for getting a copy of this Move instance
     *
     * @return a new Move that is identical to this instance
     */
    public Move getCopy(){
        return new Move(this);
    }

    /**
     * Overridden method for getting the String equivalent of this move
     * @return the 'from' and 'to' coordinates in form of String
     */
    @Override
    public String toString() {
        return from.toString() + to.toString();
    }
}
