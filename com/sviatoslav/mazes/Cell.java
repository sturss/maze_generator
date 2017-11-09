package sviatoslav.mazes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import sviatoslav.enums.Side;

class Cell extends Region {
    private Integer[] walls = {1, 1, 1, 1};
    private boolean visited = false;
    private String colorStyle= "-fx-background-color: #00766c; ";
    private String borderStyle = "-fx-border-width: 2 2 2 2; -fx-border-color: #007c91; ";
    private int length;

    Cell(int x, int y) {
        setPrefSize(x, y);
        length = 0;
        update();
    }

    void update() {
        borderStyle = "-fx-border-width:" +
                walls[Side.TOP_SIDE.getValue()] + " " +
                walls[Side.RIGHT_SIDE.getValue()] + " " +
                walls[Side.BOTTOM_SIDE.getValue()] + " " +
                walls[Side.LEFT_SIDE.getValue()] + "; " +
                "-fx-border-color: #007c91;";
        setStyle(colorStyle + borderStyle);
    }

    void deleteWall(Side side) {
        walls[side.getValue()] = 0;
    }

    void getVisited() {
        visited = true;
    }

    void buildWall(Side side) {
        walls[side.getValue()] = 1;
    }

    void setVisitedColor() {
        colorStyle = "-fx-background-color: #52c7b8; ";
    }

    void setUnvisitedColor() {
        colorStyle = "-fx-background-color: #00766c; ";
    }


    int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }

    void drawLength() {
        Label lengthLabel = new Label();
        lengthLabel.setText(String.valueOf(this.length));
        lengthLabel.setStyle("-fx-font: " + this.getPrefHeight()  / 2 + " ariel; -fx-text-fill: #00766c");
        StackPane stack = new StackPane(lengthLabel);
        stack.setPrefSize(getPrefWidth(), getPrefHeight());
        stack.setAlignment(Pos.TOP_CENTER);
        getChildren().add(stack);
    }

    boolean isVisited() {
        return visited;
    }

    boolean isWall(Side side) {
        return walls[side.getValue()] == 1;
    }
}
