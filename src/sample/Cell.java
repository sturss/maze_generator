package sample;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private Integer size_x;
    private Integer size_y;

    Cell(int x, int y) {
        size_x = x;
        size_y = y;
        update();
    }

    private void update() {
        getChildren().clear();
        if(visited) {
            setStyle("-fx-background-color: black");
        } else {
            setStyle("-fx-background-color: mediumorchid");
        }
        this.size_y = 20;
        this.size_x = 20;
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
                lines[i].setStrokeWidth(2);
                getChildren().add(lines[i]);
            }
        }
    }
    void visitFrom(int side) {
        walls[side] = 0;
        update();
    }

    void getVisited(int side) {
        visited = true;
        walls[side] = 0;
        update();
    }

    boolean isVisited() {
        return visited;
    }

    void Color() {
        setStyle("-fx-background-color: magenta");
    }
}
