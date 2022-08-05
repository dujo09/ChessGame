package com.chessgamepro.ChessPieces;

import com.chessgamepro.ChessPieces.PieceMove.PieceMove;
import com.chessgamepro.ChessPieces.PieceMove.MoveCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleCaptureCondition;
import com.chessgamepro.ChessPieces.PieceMove.SimpleMoveCondition;
import com.chessgamepro.GameBoard.Coordinate;
import com.chessgamepro.GameBoard.GameBoard;
import com.chessgamepro.GameBoard.Move;

public class Pawn extends ChessPiece{

    /**
     * Constructor for Pawn
     *
     * @param position the coordinates of Pawn
     * @param color the color of Pawn
     */
    public Pawn(Coordinate position, PieceColor color){
        super(position, PieceType.PAWN, color,
                PieceType.QUEEN, PieceType.KNIGHT, PieceType.ROOK, PieceType.BISHOP);
    }

    /**
     * Constructor for Pawn that takes if piece has moved as an argument
     *
     * @param position the coordinates of Pawn position
     * @param color the color of Pawn
     * @param hasMoved boolean saying if piece has moved before
     */
    public Pawn(Coordinate position, PieceColor color, boolean hasMoved){
        super(position, PieceType.PAWN, color, hasMoved,
                PieceType.QUEEN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK);
    }

    /**
     * Copy constructor for Pawn
     *
     * @param pawn the reference Pawn object by which a
     *               new one is created
     */
    public Pawn(Pawn pawn){
        super(pawn);
    }

    /**
     * Overridden method that checks if a given Pawn is equal to
     * this instance of Pawn
     *
     * @param obj the object checking for
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pawn)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Overridden method for Pawn that initializes the
     * movement pattern of a Pawn
     */
    @Override
    public void initializeMovementPattern() {

        // Pawn movement offsets are different depending on color
        // Pawn move and capture have an additional ability called
        // promotion, condition is that they move to the last row

        SimpleMoveCondition pawnMoveCondition = new SimpleMoveCondition(){
            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                if(pieceMoving.getColor() == PieceColor.WHITE &&
                        pieceMoving.getPosition().getRow() == 6){
                    return new Move(pieceMoving.getPosition(), to, PieceType.PAWN);

                }else if(pieceMoving.getColor() == PieceColor.BLACK &&
                        pieceMoving.getPosition().getRow() == 1){

                    return new Move(pieceMoving.getPosition(), to, PieceType.PAWN);
                }

                return super.getMove(gameBoard, pieceMoving, to);
            }
        };

        SimpleCaptureCondition pawnCaptureCondition = new SimpleCaptureCondition(){
            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                ChessPiece capturedPiece = gameBoard.getPieceAtSquare(to);

                if(pieceMoving.getColor() == PieceColor.WHITE &&
                        pieceMoving.getPosition().getRow() == 6){
                    return new Move(pieceMoving.getPosition(), to, PieceType.PAWN, capturedPiece);

                }else if(pieceMoving.getColor() == PieceColor.BLACK &&
                        pieceMoving.getPosition().getRow() == 1){

                    return new Move(pieceMoving.getPosition(), to, PieceType.PAWN, capturedPiece);
                }
                return super.getMove(gameBoard, pieceMoving, to);
            }
        };

        if(this.getColor() == PieceColor.WHITE){

            PieceMove oneForward = new PieceMove(
                    1,0,false,
                    pawnMoveCondition
            );

            PieceMove northEastCapture = new PieceMove(
                    1,1,false,
                    pawnCaptureCondition
            );

            PieceMove northWestCapture = new PieceMove(
                    1,-1,false,
                    pawnCaptureCondition
            );

            movePattern.add(oneForward);
            movePattern.add(northEastCapture);
            movePattern.add(northWestCapture);

        }else if(this.getColor() == PieceColor.BLACK){

            PieceMove oneForward = new PieceMove(
                    -1,0,false,
                    pawnMoveCondition
            );

            PieceMove southEastCapture = new PieceMove(
                    -1,1,false,
                    pawnCaptureCondition
            );

            PieceMove southWestCapture = new PieceMove(
                    -1,-1,false,
                    pawnCaptureCondition
            );

            movePattern.add(oneForward);
            movePattern.add(southEastCapture);
            movePattern.add(southWestCapture);

        }

        // Pawn can move two forward

        MoveCondition twoForwardNorthCondition = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {
                Coordinate northSquare = new Coordinate(
                        pieceMoving.getPosition().getRow() + 1,
                        pieceMoving.getPosition().getColumn()
                );

                return gameBoard.isSquareEmpty(northSquare) &&
                        gameBoard.isSquareEmpty(to) &&
                        !pieceMoving.getHasMoved();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                return new Move(pieceMoving.getPosition(), to);
            }
        };
        MoveCondition twoForwardSouthCondition = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {
                Coordinate southSquare = new Coordinate(
                        pieceMoving.getPosition().getRow() - 1,
                        pieceMoving.getPosition().getColumn()
                );

                return gameBoard.isSquareEmpty(southSquare) &&
                        gameBoard.isSquareEmpty(to) &&
                        !pieceMoving.getHasMoved();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {
                return new Move(pieceMoving.getPosition(), to);
            }
        };

        if(this.getColor() == PieceColor.WHITE){

            PieceMove twoForward = new PieceMove(
                    2,0,false,
                    twoForwardNorthCondition
            );

            movePattern.add(twoForward);

        }else if(this.getColor() == PieceColor.BLACK){

            PieceMove twoForward = new PieceMove(
                    -2,0,false,
                    twoForwardSouthCondition
            );

            movePattern.add(twoForward);
        }

        // Pawn can capture en-passant

        MoveCondition enPassantEastConditionWhite = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                Move lastMove = gameBoard.getLastMove();
                if(lastMove == null){
                    return false;
                }

                ChessPiece lastPieceMoved = gameBoard.getPieceAtSquare(lastMove.getTo());

                Coordinate squareToEast = new Coordinate(
                        pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() + 1
                );

                return pieceMoving.getPosition().getRow() == 4 &&
                        gameBoard.isSquareEmpty(to) &&
                        lastPieceMoved.getType() == PieceType.PAWN &&
                        lastMove.getTo().equals(squareToEast) &&
                        lastMove.getIsFirstMove();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                ChessPiece capturingPiece = gameBoard.getPieceAtSquare(
                        new Coordinate(
                                pieceMoving.getPosition().getRow(),
                                pieceMoving.getPosition().getColumn() + 1)
                );

                return new Move(pieceMoving.getPosition(), to, capturingPiece);
            }
        };
        MoveCondition enPassantWestConditionWhite = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                Move lastMove = gameBoard.getLastMove();
                if(lastMove == null){
                    return false;
                }

                ChessPiece lastPieceMoved = gameBoard.getPieceAtSquare(lastMove.getTo());

                Coordinate squareToWest = new Coordinate(
                        pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() - 1
                );

                return pieceMoving.getPosition().getRow() == 4 &&
                        gameBoard.isSquareEmpty(to) &&
                        lastPieceMoved.getType() == PieceType.PAWN &&
                        lastMove.getTo().equals(squareToWest) &&
                        lastMove.getIsFirstMove();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                ChessPiece capturingPiece = gameBoard.getPieceAtSquare(
                        new Coordinate(
                                pieceMoving.getPosition().getRow(),
                                pieceMoving.getPosition().getColumn() - 1)
                );

                return new Move(pieceMoving.getPosition(), to, capturingPiece);
            }
        };

        MoveCondition enPassantEastConditionBlack = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                Move lastMove = gameBoard.getLastMove();
                if(lastMove == null){
                    return false;
                }

                ChessPiece lastPieceMoved = gameBoard.getPieceAtSquare(lastMove.getTo());

                Coordinate squareToEast = new Coordinate(
                        pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() + 1
                );

                return pieceMoving.getPosition().getRow() == 3 &&
                        gameBoard.isSquareEmpty(to) &&
                        lastPieceMoved.getType() == PieceType.PAWN &&
                        lastMove.getTo().equals(squareToEast) &&
                        lastMove.getIsFirstMove();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                ChessPiece capturingPiece = gameBoard.getPieceAtSquare(
                        new Coordinate(
                                pieceMoving.getPosition().getRow(),
                                pieceMoving.getPosition().getColumn() + 1)
                );

                return new Move(pieceMoving.getPosition(), to, capturingPiece);
            }
        };
        MoveCondition enPassantWestConditionBlack = new MoveCondition() {
            @Override
            public boolean testPseudoLegal(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                Move lastMove = gameBoard.getLastMove();
                if(lastMove == null){
                    return false;
                }

                ChessPiece lastPieceMoved = gameBoard.getPieceAtSquare(lastMove.getTo());

                Coordinate squareToWest = new Coordinate(
                        pieceMoving.getPosition().getRow(),
                        pieceMoving.getPosition().getColumn() - 1
                );

                return pieceMoving.getPosition().getRow() == 3 &&
                        gameBoard.isSquareEmpty(to) &&
                        lastPieceMoved.getType() == PieceType.PAWN &&
                        lastMove.getTo().equals(squareToWest) &&
                        lastMove.getIsFirstMove();
            }

            @Override
            public Move getMove(GameBoard gameBoard, ChessPiece pieceMoving, Coordinate to) {

                ChessPiece capturingPiece = gameBoard.getPieceAtSquare(
                        new Coordinate(
                                pieceMoving.getPosition().getRow(),
                                pieceMoving.getPosition().getColumn() - 1)
                );

                return new Move(pieceMoving.getPosition(), to, capturingPiece);
            }
        };

        if(this.getColor() == PieceColor.WHITE){

            PieceMove enPassantEast = new PieceMove(
                    1,1,false,
                    enPassantEastConditionWhite
            );

            PieceMove enPassantWest = new PieceMove(
                    1,-1,false,
                    enPassantWestConditionWhite
            );

            movePattern.add(enPassantEast);
            movePattern.add(enPassantWest);

        }else if(this.getColor() == PieceColor.BLACK){

            PieceMove enPassantEast = new PieceMove(
                    -1,1,false,
                    enPassantEastConditionBlack
            );

            PieceMove enPassantWest = new PieceMove(
                    -1,-1,false,
                    enPassantWestConditionBlack
            );


            movePattern.add(enPassantEast);
            movePattern.add(enPassantWest);
        }
    }

    /**
     * Overriding method for getting a copy of this Pawn instance
     *
     * @return a new Pawn that is identical to this instance
     */
    @Override
    public Pawn getCopy(){
        return new Pawn(this);
    }
}
