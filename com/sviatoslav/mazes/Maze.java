package sviatoslav.mazes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;


public class Maze extends GridPane {
    private int rows;
    private int cols;
    private Cell[][] cells;
    private int[][] sizes = {{10, 7, 52}, {19, 11, 42},  {22, 13, 33}, {26, 14, 30},
            {31, 18, 25}, {39, 23, 20}, {52, 29, 15}};
    private MazeAlgorithm algorithm;

    public Maze(MazeAlgorithm algorithm, int rows, int cols) {
        this.algorithm = algorithm;
        cells = new Cell[rows][cols];
        int size = 10;
        for(int i =0; i < 7; i++) {
            if(rows <= sizes[i][1] && cols <= sizes[i][0]) {
                size = sizes[i][2];
                break;
            }
        }

        setPadding( new Insets(5, 5, 5,5));
        setAlignment(Pos.CENTER);
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
        algorithm.createMaze(this.cells, this.rows, this.cols);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}