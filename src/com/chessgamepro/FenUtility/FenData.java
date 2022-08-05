package com.chessgamepro.FenUtility;

import com.chessgamepro.ChessPieces.ChessPiece;
import java.util.List;

/**
 * A record holding different data types decoded from a fen string
 *
 * @param allPieces list of all chess pieces on board
 * @param boardLayout matrix representing chess board
 */
public record FenData(
        List<ChessPiece> allPieces,
        ChessPiece[][] boardLayout) {
}
