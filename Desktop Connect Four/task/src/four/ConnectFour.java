package four;

import four.core.GameBoard;
import four.core.GameLogic;
import four.gui.GameBoardView;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private GameBoardView gameBoardView;


    public ConnectFour() {
        super("Connect Four");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        buildGameBoard();
        JButton resetButton = buildResetButton();

        add(gameBoardView, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);
    }

    private void buildGameBoard() {
        GameBoard gameBoard = new GameBoard(ROWS, COLUMNS);
        GameLogic gameLogic = new GameLogic(gameBoard);
        this.gameBoardView = new GameBoardView(gameLogic);
    }

    private JButton buildResetButton() {
        JButton resetButton = new JButton("Reset game board");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener((e) -> gameBoardView.onGameReset());
        return resetButton;
    }
}