package sample;

import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;


class Maze extends GridPane {
    private Integer rows;
    private Integer cols;
    private Cell[][] cells;
    private int[][] sizes = {{19, 11, 40},  {22, 13, 35}, {26, 15, 30},
            {31, 18, 25}, {39, 23, 20}, {52, 30, 15}};

    Maze(int rows, int cols) {
        cells = new Cell[rows][cols];
        int size = 10;
        for(int i =0; i < 6; i++) {
            if(rows <= sizes[i][1] && cols <= sizes[i][0]) {
                size = sizes[i][2];
                break;
            }
        }
        setPadding( new Insets(5, 5, 0,5));
        this.rows = rows;
        this.cols = cols;
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = new Cell(size , size);
                add(c, j, i);
                cells[i][j] = c;
            }
        }
    }

        void maze() {
            Random rand = new Random();
            Stack<Point> stack = new Stack<>();
            int side;
            Vector<Integer> sides = new Vector<>(0, 4);
            int row = rand.nextInt(rows - 2) + 1;
            int col = 0;
            Point start = new Point(row, col);
            Point end = null;
            cells[row][col].getVisitedFrom(Constants.LEFT_SIDE);
            cells[row][col].setVisitedColor();
            cells[rand.nextInt(rows-1)][cols-1].visitFrom(Constants.RIGHT_SIDE);
            stack.push(new Point(row, col));
            long time = 20;
            while(true) {
                time += 20;
                PauseTransition visitFrom1 = new PauseTransition(Duration.millis(time));
                PauseTransition getVisited1 = new PauseTransition(Duration.millis(time));

                while(!checkSides(sides, row, col)) {
                    stack.pop();
                    if (stack.isEmpty()) {
                        return;
                    }
                    row = stack.lastElement().x;
                    col = stack.lastElement().y;
                }
                side = sides.get(rand.nextInt(sides.size()));

                int c = col;
                int r = row;
                int s = side;
                visitFrom1.setOnFinished(event -> cells[r][c].visitFrom(s));

                if(side == Constants.TOP_SIDE) {
                    row -= 1;
                }
                else if(side == Constants.RIGHT_SIDE) {
                    col += 1;
                }
                else if(side == Constants.BOTTOM_SIDE) {
                    row += 1;
                }
                else if(side == Constants.LEFT_SIDE) {
                    col -= 1;
                }
                stack.push(new Point(row, col));

                int c2 = col;
                int r2 = row;

                cells[r2][c2].getVisitedFrom(3 - s);
                getVisited1.setOnFinished(event -> cells[r2][c2].setVisitedColor());

                sides.clear();
                ParallelTransition transitions = new ParallelTransition(visitFrom1, getVisited1);
                transitions.play();
            }
        }


    private boolean checkSides(Vector<Integer> sides, int row, int col) {
        if(col > 0)
            if(!cells[row][col-1].isVisited())
                sides.add(Constants.LEFT_SIDE);
        if(row > 0)
            if(!cells[row-1][col].isVisited())
                sides.add(Constants.TOP_SIDE);
        if(col < this.cols -1)
            if(!cells[row][col+1].isVisited())
                sides.add(Constants.RIGHT_SIDE);
        if(row < this.rows -1)
            if(!cells[row+1][col].isVisited())
                sides.add(Constants.BOTTOM_SIDE);

        return !sides.isEmpty();
    }
}