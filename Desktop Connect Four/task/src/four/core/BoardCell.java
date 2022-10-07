package four.core;

public class BoardCell {

    private Coordinate2D position;
    private String mark = " ";


    public BoardCell(Coordinate2D position) {
        this.position = position;
    }

    public boolean isMarked() {
        return !mark.isBlank();
    }

    public void resetMark() {
        this.mark = " ";
    }

    public Coordinate2D getPosition() {
        return position;
    }

    public void setPosition(Coordinate2D position) {
        this.position = position;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
