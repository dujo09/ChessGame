package com.chessgamepro.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame implements ActionListener{

    public static Color BRIGHT_COLOR = new Color(191, 189, 193);
    public static Color HIGHLIGHTED_SQUARE_COLOR = new Color(222, 158, 54,175);
    public static Color LEGAL_MOVE_COLOR = new Color(55, 50, 62);
    public static Color CAPTURE_MOVE_COLOR = new Color(222, 158, 54);
    public static Color DARK_COLOR = new Color(109, 106, 117);

    public static Font FONT_ONE = new Font("Sefir", Font.PLAIN, 60);
    public static Font FONT_TWO = new Font("Sefir", Font.PLAIN, 40);
    public static Font FONT_THREE = new Font("Sefir", Font.PLAIN, 20);
    public static Font FONT_THREE_BOLD = new Font("Sefir", Font.BOLD, 20);


    static final String START_COMMAND = "START";
    static final String GAME_OPTIONS_COMMAND = "GAME_OPTIONS";
    static final String EXIT_COMMAND = "EXIT";

    static final String MENU_PANEL = "Menu";
    static final String GAME_OPTIONS_PANEL = "Game Options";
    static final String GAME_PANEL = "Game";

    JPanel contentPane;
    MenuPanel menuPanel;
    GameOptionsPanel gameOptionsPanel;
    GamePanel gamePanel;
    CardLayout layout;

    public Screen(){
        layout = new CardLayout();

        contentPane = new JPanel(layout);
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        gameOptionsPanel = new GameOptionsPanel(this);

        contentPane.add(menuPanel, MENU_PANEL);
        contentPane.add(gameOptionsPanel, GAME_OPTIONS_PANEL);
        contentPane.add(gamePanel, GAME_PANEL);

        add(contentPane);

        layout.show(contentPane, MENU_PANEL);

        setTitle("Chess");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    class MenuPanel extends JPanel {

        MenuPanel(){
            setLayout(new GridBagLayout());
            setBackground(BRIGHT_COLOR);

            // Title
            JLabel titleLabel = new JLabel("Chess");
            titleLabel.setFont(Screen.FONT_ONE);
            titleLabel.setForeground(Screen.LEGAL_MOVE_COLOR);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = 0;
            constraints.gridx = 0;
            constraints.weightx = 0.75;
            constraints.weighty = 1;
            constraints.gridheight = 3;
            add(titleLabel, constraints);

            // Start button
            JButton startButton = new JButton("Start");
            startButton.setFont(Screen.FONT_TWO);
            startButton.setForeground(Screen.LEGAL_MOVE_COLOR);
            startButton.setBackground(Screen.DARK_COLOR);
            startButton.setActionCommand(START_COMMAND);
            startButton.addActionListener(Screen.this);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridy = 0;
            constraints.gridx = 1;
            constraints.weightx = 0.25;
            constraints.weighty = 0.33;
            add(startButton, constraints);

            // Game options button
            JButton gameOptionsButton = new JButton("Game options");
            gameOptionsButton.setFont(Screen.FONT_TWO);
            gameOptionsButton.setForeground(Screen.LEGAL_MOVE_COLOR);
            gameOptionsButton.setBackground(Screen.DARK_COLOR);
            gameOptionsButton.setActionCommand(GAME_OPTIONS_COMMAND);
            gameOptionsButton.addActionListener(Screen.this);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridy = 1;
            constraints.gridx = 1;
            constraints.weightx = 0.25;
            constraints.weighty = 0.33;
            add(gameOptionsButton, constraints);

            // Exit button
            JButton exitButton = new JButton("Exit");
            exitButton.setFont(Screen.FONT_TWO);
            exitButton.setForeground(Screen.LEGAL_MOVE_COLOR);
            exitButton.setBackground(Screen.DARK_COLOR);
            exitButton.setActionCommand(EXIT_COMMAND);
            exitButton.addActionListener(Screen.this);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridy = 2;
            constraints.gridx = 1;
            constraints.weightx = 0.25;
            constraints.weighty = 0.33;
            add(exitButton, constraints);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch(actionCommand){
            case START_COMMAND -> {
                layout.show(contentPane, GAME_PANEL);
                gamePanel.startGame(
                        gameOptionsPanel.isWhiteAI(),
                        gameOptionsPanel.isBlackAI(),
                        gameOptionsPanel.getTimeControl()
                );

            }case GAME_OPTIONS_COMMAND -> layout.show(contentPane, GAME_OPTIONS_PANEL);
            case EXIT_COMMAND -> System.exit(0);
            case GameOptionsPanel.RETURN_TO_MENU_COMMAND -> layout.show(contentPane, MENU_PANEL);
        }
    }
}


