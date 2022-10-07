package four.gui;

import four.core.Coordinate2D;
import four.core.GameBoard;
import four.core.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

public class GameBoardView extends JPanel {

    private static final String ROWS = "123456789";
    private static final String COLUMNS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Color BASELINE_COLOR = new Color(218, 194, 255);
    private static final Color WINNING_COLOR = new Color(0, 255, 111);

    private final GameLogic gameLogic;

    private final HashMap<Coordinate2D, BoardCellButton> boardCells;
    private int rows;
    private int columns;


    public GameBoardView(GameLogic gameLogic) {
        super();
        this.gameLogic = gameLogic;
        this.boardCells = new LinkedHashMap<Coordinate2D, BoardCellButton>();

        this.rows = gameLogic.getGameBoard().getRows();
        this.columns = gameLogic.getGameBoard().getColumns();

        setLayout(new GridLayout(rows, columns));
        buildUI();
    }

    private void buildUI() {
        buildBoardCells();
    }

    private void buildBoardCells() {
        for (int row = rows - 1; row >= 0; row--) {
            for (int col = 0; col < columns; col++) {
                char rowChar = ROWS.charAt(row);
                char colChar = COLUMNS.charAt(col);

                Coordinate2D coord = new Coordinate2D(row, col);
                BoardCellButton boardCellButton = new BoardCellButton(coord);
                boardCellButton.setBackground(BASELINE_COLOR);
                boardCellButton.setName("Button" + colChar + rowChar);
                boardCellButton.setText(" ");
                boardCellButton.addActionListener((e) -> onCellClicked(coord));

                boardCells.put(coord, boardCellButton);
                add(boardCellButton);
            }
        }
    }

    private void onCellClicked(Coordinate2D coord) {
        Optional<Coordinate2D> markedCell = gameLogic.markColumnCell(coord.y);
        markedCell.ifPresent(cell -> handleCellUpdate(cell));
    }

    private void handleCellUpdate(Coordinate2D markedCell) {
        updateCell(markedCell, gameLogic.getGameBoard());

        if (gameLogic.isGameComplete()) {
            gameLogic.getWinnerCells().forEach(coord -> {
                BoardCellButton winnerCell = boardCells.get(coord);
                winnerCell.setBackground(WINNING_COLOR);
            });
        }
    }

    public void onGameReset() {
        resetCellColor();
        gameLogic.resetGame();
        updateBoard();
    }

    private void updateBoard() {
        GameBoard gameBoard = gameLogic.getGameBoard();
        boardCells.keySet().forEach(point -> updateCell(point, gameBoard));
    }

    private void updateCell(Coordinate2D coord, GameBoard gameBoard) {
        String text = gameBoard.getBoardCell(coord.x, coord.y).getMark();
        boardCells.get(coord).setText(text);
    }

    private void resetCellColor() {
        boardCells.values().forEach(cell -> cell.setBackground(BASELINE_COLOR));
    }
}
