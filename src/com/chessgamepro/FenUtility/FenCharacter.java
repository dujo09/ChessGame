package com.chessgamepro.FenUtility;

import com.chessgamepro.ChessPieces.ChessPiece;
import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.ChessPieces.PieceType;

/**
 * An enum used for fen decoding and encoding, assigns different
 * symbols for each color/type combination
 */
public enum FenCharacter {
    WHITE_KING_FEN('K'),
    BLACK_KING_FEN('k'),
    WHITE_QUEEN_FEN('Q'),
    BLACK_QUEEN_FEN('q'),
    WHITE_KNIGHT_FEN('N'),
    BLACK_KNIGHT_FEN('n'),
    WHITE_ROOK_FEN('R'),
    BLACK_ROOK_FEN('r'),
    WHITE_BISHOP_FEN('B'),
    BLACK_BISHOP_FEN('b'),
    WHITE_PAWN_FEN('P'),
    BLACK_PAWN_FEN('p'),
    NEXT_ROW_FEN('/');

    public final char symbol;
    FenCharacter(char symbol){
        this.symbol = symbol;
    }

    /**
     * Method for getting the fen character for a given ChessPiece
     * @param piece the given piece
     * @return a symbol representation of the piece
     */
    public static char getSymbol(ChessPiece piece){
        if(piece.getType() == PieceType.KING){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_KING_FEN.symbol;
            }else{
                return FenCharacter.BLACK_KING_FEN.symbol;
            }
        }else if(piece.getType() == PieceType.QUEEN){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_QUEEN_FEN.symbol;
            }else{
                return FenCharacter.BLACK_QUEEN_FEN.symbol;
            }
        }else if(piece.getType() == PieceType.KNIGHT){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_KNIGHT_FEN.symbol;
            }else{
                return FenCharacter.BLACK_KNIGHT_FEN.symbol;
            }
        }else if(piece.getType() == PieceType.ROOK){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_ROOK_FEN.symbol;
            }else{
                return FenCharacter.BLACK_ROOK_FEN.symbol;
            }
        }else if(piece.getType() == PieceType.BISHOP){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_BISHOP_FEN.symbol;
            }else{
                return FenCharacter.BLACK_BISHOP_FEN.symbol;
            }
        }else if(piece.getType() == PieceType.PAWN){

            if(piece.getColor() == PieceColor.WHITE){
                return FenCharacter.WHITE_PAWN_FEN.symbol;
            }else{
                return FenCharacter.BLACK_PAWN_FEN.symbol;
            }
        }else{
            return FenCharacter.WHITE_PAWN_FEN.symbol;
        }
    }
}
