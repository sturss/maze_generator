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
    private MazeAlgorithm algorithm;

    Maze(MazeAlgorithm a, int rows, int cols) {
        algorithm = a;
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

    void createMaze() {
        algorithm.createMaze(this.cells, this.rows, this.cols);
    }
}