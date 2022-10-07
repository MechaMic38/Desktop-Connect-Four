package four.gui;

import four.core.Coordinate2D;

import javax.swing.*;

public class BoardCellButton extends JButton {

    private final Coordinate2D coordinates;

    public BoardCellButton(Coordinate2D coordinates) {
        super();
        this.coordinates = coordinates;
        setFocusPainted(false);
    }

    public Coordinate2D getCoordinates() {
        return coordinates;
    }
}
