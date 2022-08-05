package com.chessgamepro.GUI;

import com.chessgamepro.ChessPieces.PieceColor;
import com.chessgamepro.FenUtility.FenUtility;
import com.chessgamepro.Game.Game;
import com.chessgamepro.GameBoard.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private final Game game;
    private final BoardPanel boardPanel;
    private final TimerPanel timerPanelWhite;
    private final TimerPanel timerPanelBlack;
    private final CapturedPiecesPanel capturedWhitePiecesPanel;
    private final CapturedPiecesPanel capturedBlackPiecesPanel;
    private final ColorPlayingPanel colorPlayingPanel;

    GamePanel() {
        setBackground(Screen.LEGAL_MOVE_COLOR);
        setLayout(new GridBagLayout());

        game = new Game(FenUtility.startFen, this);

        boardPanel = new BoardPanel();

        timerPanelWhite = new TimerPanel(Screen.BRIGHT_COLOR);
        timerPanelBlack = new TimerPanel(Screen.DARK_COLOR);

        capturedWhitePiecesPanel = new CapturedPiecesPanel(PieceColor.WHITE);
        capturedBlackPiecesPanel = new CapturedPiecesPanel(PieceColor.BLACK);

        colorPlayingPanel = new ColorPlayingPanel();

        // Board panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.weightx = 0.72;
        constraints.gridheight = 5;
        constraints.insets = new Insets(0,0,0,4);
        add(boardPanel, constraints);

        // Timer for Black
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weighty = 0.2;
        constraints.weightx = 0.28;
        constraints.insets = new Insets(0,0,4,0);
        add(timerPanelBlack, constraints);

        // Captured pieces by Black
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.weighty = 0.25;
        constraints.weightx = 0.28;
        constraints.insets = new Insets(0,0,4,0);
        add(capturedWhitePiecesPanel, constraints);

        // Panel showing the color whose side it is to play
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.weighty = 0.1;
        constraints.weightx = 0.28;
        constraints.insets = new Insets(0,0,4,0);
        add(colorPlayingPanel, constraints);

        // Captured pieces by White
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.weighty = 0.25;
        constraints.weightx = 0.28;
        constraints.insets = new Insets(0,0,4,0);
        add(capturedBlackPiecesPanel, constraints);

        // Timer for White
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 4;
        constraints.gridx = 1;
        constraints.weighty = 0.2;
        constraints.weightx = 0.28;
        constraints.insets = new Insets(0,0,0,0);
        add(timerPanelWhite, constraints);
    }

    /**
     * Method that instantiates all the required fields according to
     * what the user chose in the main menu and starts the game
     *
     * @param isWhiteAI is White side AI
     * @param isBlackAI is Black side AI
     * @param timeControl the selected time each side has to play
     */
    public void startGame(boolean isWhiteAI, boolean isBlackAI, Float timeControl){

        game.setIsTimeControlled(timeControl != 0);
        game.setIsWhiteAI(isWhiteAI);
        game.setIsBlackAI(isBlackAI);

        setColorPlayingPanel(PieceColor.WHITE);

        if(timeControl != 0){
            timerPanelWhite.instantiateTimer(timeControl);
            timerPanelBlack.instantiateTimer(timeControl);

            setTimerPanels(PieceColor.WHITE);

        }

        revalidate();
        repaint();

        game.instantiateGame();

    }

    class BoardPanel extends JPanel {

        int yCursor;
        int xCursor;

        BoardPanel() {
            setBackground(Screen.LEGAL_MOVE_COLOR);

            addMouseListener(new Listener());
            addMouseMotionListener(new Listener());
        }

        @Override
        public Dimension getPreferredSize() {

            return new Dimension(640,640);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            game.drawAllBoard(g,yCursor,xCursor);
        }

        class Listener extends MouseAdapter{

            @Override
            public void mousePressed(MouseEvent e) {

                game.selectPiece(yCursor,xCursor);

                revalidate();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(game.isPieceSelected()){

                    game.moveSelectedPiece( yCursor, xCursor );

                    // Update captured pieces in case a capture occurred
                    capturedWhitePiecesPanel.revalidate();
                    capturedWhitePiecesPanel.repaint();
                    capturedBlackPiecesPanel.revalidate();
                    capturedBlackPiecesPanel.repaint();
                }

                revalidate();
                repaint();

            }

            @Override
            public void mouseDragged(MouseEvent e) {

                yCursor = e.getY();
                xCursor = e.getX();

                revalidate();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                yCursor = e.getY();
                xCursor = e.getX();

                if(game.isPieceValidForSelection(yCursor,xCursor))  {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                revalidate();
                repaint();

            }

        }


    }

    class TimerPanel extends JPanel{

        private final Timer timer;
        private final JLabel timerLabelMinuteSecond;
        private final JLabel timerLabelMillisecond;
        private float timeLeft;

        TimerPanel(Color backgroundColor){

            setLayout(new GridBagLayout());
            setBackground(backgroundColor);

            timerLabelMinuteSecond = new JLabel();
            timerLabelMinuteSecond.setFont(Screen.FONT_ONE);
            timerLabelMinuteSecond.setForeground(Screen.LEGAL_MOVE_COLOR);

            timerLabelMillisecond = new JLabel();
            timerLabelMillisecond.setFont(Screen.FONT_TWO);
            timerLabelMillisecond.setForeground(Screen.LEGAL_MOVE_COLOR);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.insets = new Insets(0,0,0,5);
            add(timerLabelMinuteSecond, constraints);

            constraints = new GridBagConstraints();
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(timerLabelMillisecond, constraints);

            ActionListener timerListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeLeft -= 0.0001667f;
                    if (timeLeft <= 0) {
                        game.endGameOutOfTime();
                    }

                    updateTimerLabels(timeLeft);
                }
            };

            timer  = new Timer(10, timerListener);
        }

        /**
         * Method that instantiates the timer of this timer panel
         * to the given time control
         *
         * @param timeControl the given time control
         */
        void instantiateTimer(Float timeControl){

            timeLeft = timeControl;

            updateTimerLabels(timeLeft);
        }


        /**
         * Method that sets the text for both timer labels
         *
         * @param time time value in minutes
         */
        private void updateTimerLabels(float time){

            int minute = (int) time;

            time -= minute;
            time *= 60;
            int second = (int) time;

            time -= second;
            time *= 1000;
            int millisecond = (int)time;

            timerLabelMinuteSecond.setText(String.format("%d:%02d", minute,second));
            timerLabelMillisecond.setText(String.format("%02d",millisecond/10));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(260,130);

        }
    }

    class CapturedPiecesPanel extends JPanel{

        PieceColor capturedPiecesColor;

        CapturedPiecesPanel(PieceColor capturedPiecesColor){
            setBackground(capturedPiecesColor == PieceColor.WHITE ?
                    Screen.DARK_COLOR : Screen.BRIGHT_COLOR);

            this.capturedPiecesColor = capturedPiecesColor;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            game.drawCapturedPieces(g, capturedPiecesColor, getWidth(), getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(260,160);

        }
    }

    static class ColorPlayingPanel extends JPanel{

        private final JLabel colorPlayingLabel;

        ColorPlayingPanel(){
            setBackground(Screen.BRIGHT_COLOR);
            setLayout(new GridBagLayout());

            colorPlayingLabel = new JLabel();
            colorPlayingLabel.setFont(Screen.FONT_THREE);
            colorPlayingLabel.setForeground(Screen.LEGAL_MOVE_COLOR);

            add(colorPlayingLabel);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(260,60);

        }
    }

    /**
     * Method that sets the color and label of the color playing panel
     * according to the given color
     *
     * @param color the given color
     */
    public void setColorPlayingPanel(PieceColor color){

        StringBuilder colorPlayingString = new StringBuilder();

        if(color == PieceColor.WHITE){
            colorPlayingPanel.setBackground(Screen.BRIGHT_COLOR);
            colorPlayingString.append("White");

        }else{
            colorPlayingPanel.setBackground(Screen.DARK_COLOR);
            colorPlayingString.append("Black");

        }

        if(game.isAIInGame()){
            if(game.isColorPlayingAI()){
                colorPlayingString.append("(AI)");

            }else{
                colorPlayingString.append("(Human)");

            }
        }

        colorPlayingString.append(" now playing");

        colorPlayingPanel.colorPlayingLabel.setText(colorPlayingString.toString());
    }

    /**
     * Method that starts the right timer according to the given
     * color
     *
     * @param color the given color
     */
    public void setTimerPanels(PieceColor color){

        if(color == PieceColor.WHITE){
            timerPanelWhite.timer.start();
            timerPanelBlack.timer.stop();

        }else{
            timerPanelBlack.timer.start();
            timerPanelWhite.timer.stop();

        }

    }

    /**
     * Method for calculating the optimal board square size for the current panel
     * size
     *
     * @return the size (width and height) of a square on the board
     */
    public int getSquareSize(){
        int smallerDimension = Math.min(boardPanel.getSize().height, boardPanel.getSize().width);

        return smallerDimension/ GameBoard.ROW_COUNT;
    }

    /**
     * Method that calculates the X coordinate of the top-left corner of the board
     * so that when the board is drawn it is next to the right edge
     */
    public int getBoardTopLeftX(){
        return boardPanel.getWidth() - getSquareSize() * GameBoard.COLUM_COUNT;
    }

    /**
     * Method that calculates the Y coordinate of the top-left corner of the board
     * so that when the board is drawn it is centered vertically
     */
    public int getBoardTopLeftY(){
        return (boardPanel.getHeight() / 2) - (getSquareSize() * GameBoard.ROW_COUNT / 2);
    }

}

