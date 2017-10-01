package sample;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private Integer size_x;
    private Integer size_y;

    public Cell(int x, int y) {
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
    public void visitFrom(int side) {
        System.out.print("From this cell: break " + side + "\n");
        walls[side] = 0;
        update();
    }

    public void getVisited(int side) {
        System.out.print("To this cell: break " + side + "\n");
        visited = true;
        walls[side] = 0;
        update();
    }

    public boolean isVisited() {
        return visited;
    }

    public void Color() {
        setStyle("-fx-background-color: magenta");
    }
}
