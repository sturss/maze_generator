package sviatoslav.mazes;

import javafx.scene.layout.Region;
import sviatoslav.enums.Side;

class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private String colorStyle= "-fx-background-color: mediumorchid; ";
    private String borderStyle = "-fx-border-width: 2 2 2 2; -fx-border-color: white; ";

    Cell(int x, int y) {
        setMinWidth(x);
        setMinHeight(y);
        update();
    }

    private void update() {
        borderStyle = "-fx-border-width:" +
                walls[Side.TOP_SIDE.getValue()] + " " +
                walls[Side.RIGHT_SIDE.getValue()] + " " +
                walls[Side.BOTTOM_SIDE.getValue()] + " " +
                walls[Side.LEFT_SIDE.getValue()] + "; " +
                "-fx-border-color: white;";
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
        colorStyle = "-fx-background-color: black; ";
        update();
    }

    boolean isVisited() {
        return visited;
    }

}
