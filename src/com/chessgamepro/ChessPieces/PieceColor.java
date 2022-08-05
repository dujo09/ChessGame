package com.chessgamepro.ChessPieces;

public enum PieceColor {
    WHITE, BLACK;

    public PieceColor getOppositeColor(){
        if(this == WHITE){
            return BLACK;
        }else{
            return WHITE;
        }
    }
}
