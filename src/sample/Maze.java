package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.util.Random;
import java.util.Vector;


class Maze extends GridPane {
    private Integer rows;
    private Integer cols;
    private Cell[][] cells;

    Maze(int rows, int cols) {
        cells = new Cell[rows][cols];
        setPadding( new Insets(10, 10, 10,10));
        this.rows = rows;
        this.cols = cols;
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = new Cell(600/rows , 400/cols);
                add(c, j, i); // For a some reason 2nd parameter is col and not row like in matrix or arrays.
                cells[i][j] = c;
            }
        }
        recursive_maze();
    }

    private void recursive_maze() {
        Random rand = new Random();
        Integer side;
        int row = rand.nextInt(rows - 2) + 1;
        int col = 0;
        Vector<Integer> sides = new Vector<>(0, 4);
        sides.add(Constants.TOP_SIDE);
        sides.add(Constants.RIGHT_SIDE);
        sides.add(Constants.BOTTOM_SIDE);
        cells[row][col].getVisited(Constants.LEFT_SIDE);
        System.out.print("X = "+col+"Y = "+row+"\n");
        while(sides.size() > 0) {
            System.out.print("X = "+col+"Y = "+row+"\n");
            side = sides.get(rand.nextInt(sides.size()));
            cells[row][col].visitFrom(side);
            if(side.equals(Constants.TOP_SIDE)) {
                row -= 1;
            }
            else if(side.equals(Constants.RIGHT_SIDE)) {
                col += 1;
            }
            else if(side.equals(Constants.BOTTOM_SIDE)) {
                row += 1;
            }
            else if(side.equals(Constants.LEFT_SIDE)) {
                col -= 1;
            }

            cells[row][col].getVisited(3-side);
            sides.clear();

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
        }
        cells[row][col].Color();
    }
}