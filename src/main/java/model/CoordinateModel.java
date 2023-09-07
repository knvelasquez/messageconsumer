package model;

public class CoordinateModel {
    private final int x;
    private final int y;
    private final String message;

    public CoordinateModel(int x, int y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMessage() {
        return message;
    }
}
