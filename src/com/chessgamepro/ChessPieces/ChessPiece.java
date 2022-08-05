package com.chessgamepro.ChessPieces;
import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.GUI.ImageUtility;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that all chess pieces extend
 */
public abstract class ChessPiece {

    //Constant representing height and width of piece image
    public static final int PIECE_IMAGE_SIZE = GameBoard.WIDTH/8;
    private Image pieceImage;
    private Coordinate position;
    private PieceType type;
    private PieceColor color;
    private boolean hasMoved;
    protected List<PieceMove> movePattern;
    private final PieceType[] promotions;

    /**
     * Constructor for a ChessPiece
     *
     * @param position the position of piece on the board
     * @param type the type of piece
     * @param color the color of piece
     * @param promotions all piece types that this piece can promote into
     */
    public ChessPiece(Coordinate position, PieceType type, PieceColor color, PieceType...promotions) {

        this.position = position;
        this.type = type;
        this.color = color;

        hasMoved = false;

        movePattern = new ArrayList<>();
        initializeMovementPattern();

        this.promotions = promotions;

        pieceImage = ImageUtility.initializePieceImage(type,color);
    }

    /**
     * Constructor for a ChessPiece that takes if piece has moved as argument
     *
     * @param position the position of piece on the board
     * @param type the type of piece
     * @param color the color of piece
     * @param hasMoved boolean saying if piece has moved before
     * @param promotions all piece types that this piece can promote into
     */
    public ChessPiece(Coordinate position, PieceType type, PieceColor color, boolean hasMoved,
                      PieceType...promotions) {

        this.position = position;
        this.type = type;
        this.color = color;

        this.hasMoved = hasMoved;

        movePattern = new ArrayList<>();
        initializeMovementPattern();

        this.promotions = promotions;

        pieceImage = ImageUtility.initializePieceImage(type,color);
    }

    /**
     * Copy constructor for ChessPiece
     *
     * @param piece the reference ChessPiece by which a new
     *              piece is created
     */
    public ChessPiece(ChessPiece piece) {

        this.position = piece.position;
        this.type = piece.type;
        this.color = piece.color;

        this.hasMoved = piece.hasMoved;

        this.movePattern = piece.movePattern;

        this.promotions = piece.promotions;

        this.pieceImage = piece.pieceImage;
    }

    /**
     * Method returns a new ChessPiece with given type, used for piece
     * promotion
     *
     * @param promotedPieceType the type of new piece
     * @return a ChessPiece that is identical to this instance except that
     * its type and all fields associated with it are updated
     */
    public ChessPiece getPromotedPiece(PieceType promotedPieceType){

        switch (promotedPieceType){
            case KING -> {return new King(position,color, hasMoved);}
            case QUEEN -> {return new Queen(position,color, hasMoved);}
            case KNIGHT -> {return new Knight(position,color, hasMoved);}
            case BISHOP -> {return new Bishop(position,color, hasMoved);}
            case ROOK -> {return new Rook(position,color, hasMoved);}
            default -> {return new Pawn(position,color, hasMoved);}
        }
    }

    /**
     * Overridden method that checks if a given ChessPiece is equal to
     * this instance of ChessPiece
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof ChessPiece otherPiece)) {
            return false;
        }

        return position.equals(otherPiece.getPosition()) &&
                type == otherPiece.type &&
                color == otherPiece.color &&
                hasMoved == otherPiece.hasMoved;
    }

    /**
     * Abstract method for getting a copy of this ChessPiece instance
     *
     * @return a new ChessPiece that is identical to this instance
     */
    public abstract ChessPiece getCopy();

    /**
     * Overridden method that returns a String representation of this
     * ChessPiece instance
     *
     * @return the color and type of piece in form of a String
     */
    @Override
    public String toString() {
        return color.name() + " " + type.name();
    }

    // Methods for getting and setting piece position
    public Coordinate getPosition() {
        return position;
    }
    public void setPosition(Coordinate newPosition) {
        position = newPosition;
    }

    // Methods for getting and setting piece color
    public PieceColor getColor() {
        return color;
    }
    public void setColor(PieceColor color) {
        this.color = color;
    }

    // Methods for getting and setting piece type
    public PieceType getType() {
        return type;
    }
    public void setType(PieceType type) {
        this.type = type;
    }

    // Methods for getting and setting if piece has moved
    public boolean getHasMoved(){
        return hasMoved;
    }
    public void setHasMoved(boolean newHasMoved){
        hasMoved = newHasMoved;
    }

    //Method for getting and initializing movement pattern of piece
    public List<PieceMove> getMovePattern(){return movePattern;}

    /**
     * Abstract method for initializing the movement pattern of a piece
     */
    public abstract void initializeMovementPattern();

    /**
     * Method that returns all the piece types that this piece can
     * promote into
     *
     * @return an array of all the piece types
     */
    public PieceType[] getPromotions(){
        return promotions;
    }

    // Methods for getting and setting piece image
    public Image getPieceImage() {
        return pieceImage;
    }

    /**
     * Method that sets piece image from new image path
     *
     * @param path new image path
     */
    public void setPieceImage(String path) {
        pieceImage = ImageUtility.getImageAtPath(path);
    }

}
