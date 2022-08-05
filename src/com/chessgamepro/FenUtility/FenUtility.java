package com.chessgamepro.FenUtility;

import com.chessgamepro.ChessPieces.*;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;

import java.util.ArrayList;
import java.util.List;


public final class FenUtility {

    //Constant representing the typical start of a chess game
    public static final String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    public static final String position2 = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";

    /**
     * Method for decoding data held within a fen string
     *
     * @param fen string holding data about the board
     * @return data decoded from fen string
     */
    public static FenData getDataFromFen(String fen) {

        //Data to be decoded from fen string
        List<ChessPiece> allPieces = new ArrayList<>();
        ChessPiece[][] boardLayout =
                new ChessPiece[GameBoard.ROW_COUNT][GameBoard.COLUM_COUNT];

        //Split the fen into different sections where each holds its own data types
        String[] fenSections = fen.split(" ");

        int lenghtOfSection = fenSections[0].length();

        //Section one holds the all data about piece positions, types and colors
        int rowCounter = 7;
        int columnCounter = 0;


        for (int i = 0; i < lenghtOfSection; ++i) {
            char symbol = fenSections[0].charAt(i);;

            if (symbol == FenCharacter.NEXT_ROW_FEN.symbol) {
                --rowCounter;
                columnCounter = 0;

            } else if (Character.isDigit(symbol)) {
                columnCounter += Character.getNumericValue(symbol);

            } else {

                if (symbol == FenCharacter.WHITE_KING_FEN.symbol) {
                    allPieces.add(new King(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                } else if (symbol == FenCharacter.BLACK_KING_FEN.symbol) {
                    allPieces.add(new King(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                } else if (symbol == FenCharacter.WHITE_QUEEN_FEN.symbol) {
                    allPieces.add(new Queen(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                } else if (symbol == FenCharacter.BLACK_QUEEN_FEN.symbol) {
                    allPieces.add(new Queen(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                } else if (symbol == FenCharacter.WHITE_KNIGHT_FEN.symbol) {
                    allPieces.add(new Knight(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                } else if (symbol == FenCharacter.BLACK_KNIGHT_FEN.symbol) {
                    allPieces.add(new Knight(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                } else if (symbol == FenCharacter.WHITE_ROOK_FEN.symbol) {
                    allPieces.add(new Rook(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                } else if (symbol == FenCharacter.BLACK_ROOK_FEN.symbol) {
                    allPieces.add(new Rook(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                } else if (symbol == FenCharacter.WHITE_BISHOP_FEN.symbol) {
                    allPieces.add(new Bishop(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                } else if (symbol == FenCharacter.BLACK_BISHOP_FEN.symbol) {
                    allPieces.add(new Bishop(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                } else if (symbol == FenCharacter.BLACK_PAWN_FEN.symbol) {
                    allPieces.add(new Pawn(new Coordinate(rowCounter, columnCounter), PieceColor.BLACK));

                }else{
                    //Coding decision - White Pawn is always the default case
                    allPieces.add(new Pawn(new Coordinate(rowCounter, columnCounter), PieceColor.WHITE));

                }

                ++columnCounter;
            }
        }

        //Decode the boardLayout based on list of pieces and their positions
        for(ChessPiece piece : allPieces){
            boardLayout[piece.getPosition().getRow()][piece.getPosition().getColumn()] = piece;
        }

        return new FenData(allPieces, boardLayout);
    }

    public static String getFenStringFromData(FenData fenData){

        StringBuilder fen = new StringBuilder();

        int nullCounter = 0;

        // Decode fen position String from boardLayout
        for(int i = 7; i >= 0; --i){
            for(int j = 0; j < GameBoard.COLUM_COUNT; ++j){
                ChessPiece temp = fenData.boardLayout()[i][j];

                if(temp == null){

                    ++nullCounter;
                    continue;
                }

                if(nullCounter != 0){

                    fen.append(nullCounter);
                    nullCounter = 0;
                }

                fen.append(FenCharacter.getSymbol(temp));
            }

            if(nullCounter != 0){
                fen.append(nullCounter);
                nullCounter = 0;
            }

            fen.append(FenCharacter.NEXT_ROW_FEN.symbol);
        }

        return fen.toString();
    }
}