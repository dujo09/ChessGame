package com.chessgamepro.GameBoard;

/**
 * A class that groups together the row and column of a square
 * on a chess board
 */
public class Coordinate {
    private int row;
    private int column;

    /**
     * Constructor for Coordinate
     *
     * @param column the column number of a square
     * @param row the row number of a square
     */
    public Coordinate(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Copy constructor for Coordinate
     *
     * @param coordinate the reference coordinate by which a new
     *                   one is created
     */
    public Coordinate(Coordinate coordinate){
        this.row = coordinate.row;
        this.column = coordinate.column;
    }

    /**
     * Overridden method that checks if a given Coordinate is equal to
     * this instance of Coordinate
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Coordinate otherCoordinate)){
            return false;
        }

        return (otherCoordinate.row == row) &&
                (otherCoordinate.column == column);
    }

    //Methods for getting and setting the row
    public int getRow(){
        return row;
    }
    public void setRow(int newRow){
        row = newRow;
    }

    //Methods for getting and setting the column
    public int getColumn(){
        return column;
    }
    public void setColumn(int newColumn) {
        column = newColumn;
    }

    // Methods for increasing and decreasing the row and column
    public void increaseRow(int value){
        row += value;
    }
    public void decreaseRow(int value){
        row -= value;
    }
    public void increaseColumn(int value){
        column += value;
    }
    public void decreaseColumn(int value){
        column -= value;
    }


    /**
     * Method for copying this Coordinate instance
     *
     * @return a new coordinate that is identical to this instance
     */
    public Coordinate getCopy(){
        return new Coordinate(this);
    }

    /**
     * Overridden method that returns the String equvialent of
     * this Coordinate
     *
     * @return the 'row' and 'column' in form of String
     */
    @Override
    public String toString() {
        char rank = (char)('1' + row);
        char file = (char)('a'+ column);

        return file+""+rank;
    }
}
