package four.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameLogic {

    private final static int ADJACENT_CELLS = 4;

    private final GameBoard gameBoard;
    private int userTurn = 1;
    private boolean gameComplete = false;

    private List<Coordinate2D> winnerCells;


    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.winnerCells = new LinkedList<>();
    }

    public Optional<Coordinate2D> markColumnCell(int column) {
        if (isGameComplete()) {
            return Optional.empty();
        }

        for (int row = 0; row < gameBoard.getRows(); row++) {
            BoardCell cell = gameBoard.getBoardCell(row, column);
            if (cell.isMarked()) continue;

            gameBoard.markCell(
                    new Coordinate2D(row, column),
                    getUserMark()
            );

            if (!checkGameComplete(cell)) {
                changeUserTurn();
            }

            return Optional.of(cell.getPosition());
        }
        return Optional.empty();
    }

    private void changeUserTurn() {
        userTurn++;
    }

    private String getUserMark() {
        return userTurn % 2 == 0 ? "O" : "X";
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void resetGame() {
        gameBoard.reset();
        userTurn = 1;
        gameComplete = false;
        winnerCells.clear();
    }

    private boolean checkGameComplete(BoardCell markedCell) {
        return checkHorizontally(markedCell) || checkVertically(markedCell) || checkDiagonally(markedCell);
    }

    private boolean checkHorizontally(BoardCell markedCell) {
        List<BoardCell> cells = gameBoard.getRowCells(
                markedCell.getPosition().x
        );
        return checkAdjacency(cells, markedCell.getMark());
    }

    private boolean checkVertically(BoardCell markedCell) {
        List<BoardCell> cells = gameBoard.getColumnCells(
                markedCell.getPosition().y
        );
        return checkAdjacency(cells, markedCell.getMark());
    }

    private boolean checkDiagonally(BoardCell markedCell) {
        return checkLeftDiagonal(markedCell) || checkRightDiagonal(markedCell);
    }

    private boolean checkLeftDiagonal(BoardCell markedCell) {
        List<BoardCell> cells = gameBoard.getLeftDiagonalCells(
                markedCell.getPosition().x,
                markedCell.getPosition().y
        );
        return checkAdjacency(cells, markedCell.getMark());
    }

    private boolean checkRightDiagonal(BoardCell markedCell) {
        List<BoardCell> cells = gameBoard.getRightDiagonalCells(
                markedCell.getPosition().x,
                markedCell.getPosition().y
        );
        return checkAdjacency(cells, markedCell.getMark());
    }

    private boolean checkAdjacency(List<BoardCell> cells, String mark) {
        List<Coordinate2D> adjacentCells = new LinkedList<>();
        for (BoardCell cell : cells) {
            if (mark.equals(cell.getMark())) {
                adjacentCells.add(cell.getPosition());
            } else {
                adjacentCells.clear();
            }

            if (adjacentCells.size() == ADJACENT_CELLS) {
                winnerCells = adjacentCells;
                gameComplete = true;
                return true;
            }
        }

        return false;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getUserTurn() {
        return userTurn;
    }

    public List<Coordinate2D> getWinnerCells() {
        return winnerCells;
    }
}
