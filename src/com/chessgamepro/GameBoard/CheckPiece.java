package com.chessgamepro.GameBoard;

import com.chessgamepro.ChessPieces.ChessPiece;

/**
 * Interface used by GameBoard for getting a piece of
 * specified attributes
 */
public interface CheckPiece {
    boolean test(ChessPiece piece);
}
