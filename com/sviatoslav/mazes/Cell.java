package sviatoslav.mazes;

import javafx.scene.layout.Region;
import sviatoslav.enums.Side;

class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private String colorStyle= "-fx-background-color: #00766c; ";
    private String borderStyle = "-fx-border-width: 2 2 2 2; -fx-border-color: #007c91; ";

    Cell(int x, int y) {
        setMinWidth(x);
        setMinHeight(y);
        update();
    }

    private void update() {
        borderStyle = "-fx-border-width:" +
                2*walls[Side.TOP_SIDE.getValue()] + " " +
                2*walls[Side.RIGHT_SIDE.getValue()] + " " +
                2*walls[Side.BOTTOM_SIDE.getValue()] + " " +
                2*walls[Side.LEFT_SIDE.getValue()] + "; " +
                "-fx-border-color: #007c91;";
        setStyle(colorStyle + borderStyle);
    }

    void deleteWall(Side side) {
        walls[side.getValue()] = 0;
        update();
    }

    void getVisited() {
        visited = true;
    }

    void buildWall(Side side) {
        walls[side.getValue()] = 1;
        update();
    }

    void setVisitedColor() {
        colorStyle = "-fx-background-color: #52c7b8; ";
        update();
    }

    boolean isVisited() {
        return visited;
    }

}
