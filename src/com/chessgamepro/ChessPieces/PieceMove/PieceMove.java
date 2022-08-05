package com.chessgamepro.ChessPieces.PieceMove;

/**
 * Class that describes a certain move of a piece
 * with exact offsets and conditions
 */
public class PieceMove {

    private final int rowOffset;
    private final int columnOffset;
    private final boolean isSliding;
    private final MoveCondition[] moveConditions;


    /**
     * Constructor for PieceMove
     *
     * @param rowOffset row offset for this type of move for a piece
     * @param columnOffset column offset for this type of move for a piece
     * @param isSliding boolean value which describes if this kind of move
     *                  is a sliding move
     * @param moveConditions a number of conditions under which a piece can move
     *                       with these offsets
     */
    public PieceMove(int rowOffset, int columnOffset, boolean isSliding,
                     MoveCondition...moveConditions){

        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
        this.isSliding = isSliding;

        this.moveConditions = moveConditions;
    }

    public MoveCondition[] getMoveConditions(){
        return moveConditions;
    }

    // Methods for getting offsets
    public int getRowOffset(){
        return rowOffset;
    }
    public int getColumnOffset(){
        return columnOffset;
    }

    // Methods for getting if this kind of move is a sliding move
    public boolean isSliding(){
        return isSliding;
    }
}
