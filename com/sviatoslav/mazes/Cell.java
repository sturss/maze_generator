package sviatoslav.mazes;

import sviatoslav.enums.Side;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private Integer size_x;
    private Integer size_y;
    private String color = "-fx-background-color: mediumorchid";

    Cell(int x, int y) {
        size_x = x;
        size_y = y;
        update();
    }

    private void update() {
        getChildren().clear();
        setStyle(color);
        Line[] lines = new Line[4];

        if(walls[0] == 1)
            lines[0] = new Line(0, 0, size_x, 0);
        if(walls[1] == 1)
            lines[1] = new Line(size_x, 0, size_x, size_y);
        if(walls[3] == 1)
            lines[2] = new Line(size_x, size_y, 0, size_y);
        if(walls[2] == 1)
            lines[3] = new Line(0, size_y, 0, 0);
        for (int i=0; i < 4; i++) {
            if(lines[i] != null) {
                lines[i].setStroke(Color.WHITESMOKE);
                lines[i].setStrokeWidth(1);
                getChildren().add(lines[i]);
            }
        }
    }
    void visitFrom(Side side) {
        walls[side.getValue()] = 0;
        update();
    }

    void getVisitedFrom(Side side) {
        visited = true;
        walls[side.getValue()] = 0;
        update();
    }

    void buildWall(Side side) {
        walls[side.getValue()] = 1;
        update();
    }

    void setVisitedColor() {
        color = "-fx-background-color: black";
        update();
    }

    boolean isVisited() {
        return visited;
    }

}
