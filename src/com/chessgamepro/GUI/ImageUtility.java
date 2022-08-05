package com.chessgamepro.GUI;

import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.ChessPieces.PieceType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtility {

    //Constants representing file paths to different piece images
    public static final String WHITE_KING_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wk.png";
    public static final String BLACK_KING_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/bk.png";
    public static final String WHITE_QUEEN_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wq.png";
    public static final String BLACK_QUEEN_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/bq.png";
    public static final String WHITE_KNIGHT_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wn.png";
    public static final String BLACK_KNIGHT_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/bn.png";
    public static final String WHITE_ROOK_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wr.png";
    public static final String BLACK_ROOK_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/br.png";
    public static final String WHITE_BISHOP_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wb.png";
    public static final String BLACK_BISHOP_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/bb.png";
    public static final String WHITE_PAWN_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/wp.png";
    public static final String BLACK_PAWN_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/bp.png";

    public static final String GAME_BOARD_IMAGE_PATH =
            "src/com/chessgamepro/GUI/Resources/Images/board.png";

    /**
     * Method for initializing the image of a chess piece based on its type and colors
     *
     * @param type the type of piece (Queen, King, Pawn...)
     * @param color the color of piece
     * @return the image of piece
     */
    public static Image initializePieceImage(PieceType type, PieceColor color) {

        if (color == PieceColor.WHITE) {
            switch (type) {
                case KING -> {
                    return getImageAtPath(WHITE_KING_IMAGE_PATH);
                }
                case QUEEN -> {
                    return getImageAtPath(WHITE_QUEEN_IMAGE_PATH);
                }
                case KNIGHT -> {
                    return getImageAtPath(WHITE_KNIGHT_IMAGE_PATH);
                }
                case ROOK -> {
                    return getImageAtPath(WHITE_ROOK_IMAGE_PATH);
                }
                case BISHOP -> {
                    return getImageAtPath(WHITE_BISHOP_IMAGE_PATH);
                }
                default -> {
                    return getImageAtPath(WHITE_PAWN_IMAGE_PATH);
                }
            }
        } else {
            switch (type) {
                case KING -> {
                    return getImageAtPath(BLACK_KING_IMAGE_PATH);
                }
                case QUEEN -> {
                    return getImageAtPath(BLACK_QUEEN_IMAGE_PATH);
                }
                case KNIGHT -> {
                    return getImageAtPath(BLACK_KNIGHT_IMAGE_PATH);
                }
                case ROOK -> {
                    return getImageAtPath(BLACK_ROOK_IMAGE_PATH);
                }
                case BISHOP -> {
                    return getImageAtPath(BLACK_BISHOP_IMAGE_PATH);
                }
                case PAWN -> {
                    return getImageAtPath(BLACK_PAWN_IMAGE_PATH);
                }
                default -> {
                    return getImageAtPath(WHITE_PAWN_IMAGE_PATH);
                }
            }
        }

    }

    /**
     * Method that retrieves an image at a specified path
     *
     * @param path string representing the path to file
     * @return image retrieved from file
     */
    public static Image getImageAtPath(String path) {

        Image image = null;

        try {

            image = ImageIO.read(new File(path));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return image;
    }

}