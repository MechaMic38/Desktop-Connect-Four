package four.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {

    private final HashMap<Coordinate2D, BoardCell> boardCells;
    private final int rows;
    private final int columns;

    public GameBoard(int rows, int columns) {
        this.boardCells = new LinkedHashMap<>();
        this.rows = rows;
        this.columns = columns;

        buildBoard();
    }

    public void markCell(Coordinate2D coord, String mark) {
        boardCells.get(coord).setMark(mark);
    }

    private void buildBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Coordinate2D coord = new Coordinate2D(row, col);
                BoardCell boardCell = new BoardCell(coord);

                boardCells.put(coord, boardCell);
            }
        }
    }

    public BoardCell getBoardCell(int x, int y) {
        return boardCells.get(new Coordinate2D(x, y));
    }

    /**
     * Gets all cells that belong to the selected row.
     *
     * @param row Board row
     * @return List of {@link BoardCell}
     */
    protected List<BoardCell> getRowCells(int row) {
        return boardCells.values().stream()
                .filter(cell -> cell.getPosition().x == row)
                .collect(Collectors.toList());
    }

    /**
     * Gets all cells that belong to the selected column.
     *
     * @param column Board column
     * @return List of {@link BoardCell}
     */
    protected List<BoardCell> getColumnCells(int column) {
        return boardCells.values().stream()
                .filter(cell -> cell.getPosition().y == column)
                .collect(Collectors.toList());
    }

    /**
     * Gets all cells that sit on the {@code y=x} diagonal that crosses the selected coordinate.
     *
     * @param row    Board row
     * @param column Board column
     * @return List of {@link BoardCell}
     */
    protected List<BoardCell> getRightDiagonalCells(int row, int column) {
        List<BoardCell> cells = new LinkedList<>();
        int rowCopy = row;
        int colCopy = column;

        //Scan top-right direction
        while (isValidRow(rowCopy) && isValidColumn(colCopy)) {
            cells.add(getBoardCell(rowCopy, colCopy));
            rowCopy++;
            colCopy++;
        }

        rowCopy = row - 1;
        colCopy = column - 1;

        //Scan bottom-left direction
        while (isValidRow(rowCopy) && isValidColumn(colCopy)) {
            cells.add(0, getBoardCell(rowCopy, colCopy)); //keeps the cell order
            rowCopy--;
            colCopy--;
        }

        return cells;
    }

    /**
     * Gets all cells that sit on the {@code y=-x} diagonal that crosses the selected coordinate.
     *
     * @param row    Board row
     * @param column Board column
     * @return List of {@link BoardCell}
     */
    protected List<BoardCell> getLeftDiagonalCells(int row, int column) {
        List<BoardCell> cells = new LinkedList<>();
        int rowCopy = row;
        int colCopy = column;

        //Scan top-left direction
        while (isValidRow(rowCopy) && isValidColumn(colCopy)) {
            cells.add(getBoardCell(rowCopy, colCopy));
            rowCopy++;
            colCopy--;
        }

        rowCopy = row - 1;
        colCopy = column + 1;

        //Scan bottom-right direction
        while (isValidRow(rowCopy) && isValidColumn(colCopy)) {
            cells.add(0, getBoardCell(rowCopy, colCopy)); //keeps the cell order
            rowCopy--;
            colCopy++;
        }

        return cells;
    }

    public void reset() {
        boardCells.values().forEach(BoardCell::resetMark);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    private boolean isValidRow(int row) {
        return row >= 0 && row < rows;
    }

    private boolean isValidColumn(int column) {
        return column >= 0 && column < columns;
    }
}
