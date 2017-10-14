package sviatoslav.mazes;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;


public class Maze extends GridPane {
    private Integer rows;
    private Integer cols;
    private Cell[][] cells;
    private int[][] sizes = {{19, 11, 40},  {22, 13, 35}, {26, 15, 30},
            {31, 18, 25}, {39, 23, 20}, {52, 30, 15}};
    private MazeAlgorithm algorithm;

    public Maze(MazeAlgorithm a, int rows, int cols) {
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

    public void createMaze() {

        algorithm.createMaze(this.cells, this.rows, this.cols
        );
    }
}