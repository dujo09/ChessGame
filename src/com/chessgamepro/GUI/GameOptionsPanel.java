package com.chessgamepro.GUI;

import com.chessgamepro.ChessPieces.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOptionsPanel extends JPanel implements ActionListener {

    static final String RETURN_TO_MENU_COMMAND = "return";
    static final String WHITE_HUMAN_COMMAND = "white human";
    static final String BLACK_HUMAN_COMMAND = "black human";
    static final String WHITE_AI_COMMAND = "white AI";
    static final String BLACK_AI_COMMAND = "black AI";

    private final Screen screen;
    private boolean isWhiteAI;
    private boolean isBlackAI;
    private float timeControl;

    GameOptionsPanel(Screen screen){
        setLayout(new GridBagLayout());
        setBackground(Screen.BRIGHT_COLOR);

        this.screen = screen;

        isWhiteAI = false;
        isBlackAI = false;
        timeControl = 0;

        // Panel for choosing who is playing each side
        JPanel playerOptionsPanel = new JPanel(new GridBagLayout());
        playerOptionsPanel.setBackground(Screen.BRIGHT_COLOR);

        //      Label for options for White
        JLabel whiteOptionsLabel = new JLabel("White side");
        whiteOptionsLabel.setFont(Screen.FONT_THREE_BOLD);
        whiteOptionsLabel.setForeground(Screen.LEGAL_MOVE_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 0.33;
        constraints.weightx = 0.5;
        playerOptionsPanel.add(whiteOptionsLabel, constraints);

        //          Human player radio button
        JRadioButton whiteHumanButton = new JRadioButton("Human");
        whiteHumanButton.setFont(Screen.FONT_THREE);
        whiteHumanButton.setForeground(Screen.LEGAL_MOVE_COLOR);
        whiteHumanButton.setBackground(Screen.BRIGHT_COLOR);
        whiteHumanButton.setActionCommand(WHITE_HUMAN_COMMAND);
        whiteHumanButton.addActionListener(this);
        whiteHumanButton.setSelected(true);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weightx = 0.25;
        constraints.weighty = 0.33;
        playerOptionsPanel.add(whiteHumanButton, constraints);

        //          AI player radio button
        JRadioButton whiteAIButton = new JRadioButton("AI");
        whiteAIButton.setFont(Screen.FONT_THREE);
        whiteAIButton.setForeground(Screen.LEGAL_MOVE_COLOR);
        whiteAIButton.setBackground(Screen.BRIGHT_COLOR);
        whiteAIButton.setActionCommand(WHITE_AI_COMMAND);
        whiteAIButton.addActionListener(this);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridx = 2;
        constraints.weightx = 0.25;
        constraints.weighty = 0.33;
        playerOptionsPanel.add(whiteAIButton, constraints);

        ButtonGroup whitePlayerButtons = new ButtonGroup();
        whitePlayerButtons.add(whiteHumanButton);
        whitePlayerButtons.add(whiteAIButton);

        //      Label for options for Black
        JLabel blackOptionsLabel = new JLabel("Black side");
        blackOptionsLabel.setFont(Screen.FONT_THREE_BOLD);
        blackOptionsLabel.setForeground(Screen.LEGAL_MOVE_COLOR);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weighty = 0.33;
        constraints.weightx = 0.5;
        playerOptionsPanel.add(blackOptionsLabel, constraints);

        //          Human player radio button
        JRadioButton blackHumanButton = new JRadioButton("Human");
        blackHumanButton.setFont(Screen.FONT_THREE);
        blackHumanButton.setForeground(Screen.LEGAL_MOVE_COLOR);
        blackHumanButton.setBackground(Screen.BRIGHT_COLOR);
        blackHumanButton.setActionCommand(BLACK_HUMAN_COMMAND);
        blackHumanButton.addActionListener(this);
        blackHumanButton.setSelected(true);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.weightx = 0.25;
        constraints.weighty = 0.33;
        playerOptionsPanel.add(blackHumanButton, constraints);

        //          AI player radio button
        JRadioButton blackAIButton = new JRadioButton("AI");
        blackAIButton.setFont(Screen.FONT_THREE);
        blackAIButton.setForeground(Screen.LEGAL_MOVE_COLOR);
        blackAIButton.setBackground(Screen.BRIGHT_COLOR);
        blackAIButton.setActionCommand(BLACK_AI_COMMAND);
        blackAIButton.addActionListener(this);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 2;
        constraints.weightx = 0.25;
        constraints.weighty = 0.33;
        playerOptionsPanel.add(blackAIButton, constraints);

        ButtonGroup blackPlayerButtons = new ButtonGroup();
        blackPlayerButtons.add(blackHumanButton);
        blackPlayerButtons.add(blackAIButton);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 0.25;
        constraints.weightx = 1;
        add(playerOptionsPanel, constraints);

        // Time control panel
        JPanel timeControlPanel = new JPanel(new GridBagLayout());
        timeControlPanel.setBackground(Screen.BRIGHT_COLOR);

        //      Time control label
        JLabel timeControlLabel = new JLabel("Choose a time control");
        timeControlLabel.setFont(Screen.FONT_THREE_BOLD);
        timeControlLabel.setForeground(Screen.LEGAL_MOVE_COLOR);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 0.25;
        constraints.weightx = 1;
        timeControlPanel.add(timeControlLabel, constraints);

        //      List of time control options
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("None");
        listModel.addElement("1 min");
        listModel.addElement("2 min");
        listModel.addElement("3 min");
        listModel.addElement("4 min");
        listModel.addElement("5 min");
        listModel.addElement("6 min");
        listModel.addElement("7 min");
        listModel.addElement("8 min");
        listModel.addElement("9 min");
        listModel.addElement("10 min");
        listModel.addElement("15 min");
        listModel.addElement("20 min");
        listModel.addElement("30 min");
        listModel.addElement("60 min");

        JList<String> timeControlList = new JList<>(listModel);
        timeControlList.setFont(Screen.FONT_THREE);
        timeControlList.setForeground(Screen.LEGAL_MOVE_COLOR);
        timeControlList.setBackground(Screen.HIGHLIGHTED_SQUARE_COLOR);
        timeControlList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        timeControlList.setLayoutOrientation(JList.VERTICAL_WRAP);
        timeControlList.setVisibleRowCount(3);
        timeControlList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                String selectedTimeControl = timeControlList.getSelectedValue();
                if(selectedTimeControl.equals("None")){
                    this.timeControl = 0;
                    return;
                }

                String[] sections = selectedTimeControl.split(" ");
                this.timeControl = Float.parseFloat(sections[0]);

            }
        });

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weighty = 0.75;
        constraints.weightx = 1;
        timeControlPanel.add(timeControlList, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.weighty = 0.4;
        constraints.weightx = 1;
        add(timeControlPanel, constraints);

        // Return button
        JButton returnButton = new JButton("Save and return");
        returnButton.setFont(Screen.FONT_THREE_BOLD);
        returnButton.setForeground(Screen.LEGAL_MOVE_COLOR);
        returnButton.setBackground(Screen.DARK_COLOR);
        returnButton.setActionCommand(RETURN_TO_MENU_COMMAND);
        returnButton.addActionListener(screen);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.weighty = 0.1;
        constraints.weightx = 1;
        add(returnButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand){
            case WHITE_HUMAN_COMMAND -> isWhiteAI = false;
            case WHITE_AI_COMMAND -> isWhiteAI = true;
            case BLACK_HUMAN_COMMAND -> isBlackAI = false;
            case BLACK_AI_COMMAND -> isBlackAI = true;
        }
    }

    private PieceColor getRandomColor(){
        double randomNumber = Math.random();

        return randomNumber <= 0.5 ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public boolean isWhiteAI(){
        return isWhiteAI;
    }

    public boolean isBlackAI(){
        return isBlackAI;
    }

    public float getTimeControl(){
        return timeControl;
    }
}
