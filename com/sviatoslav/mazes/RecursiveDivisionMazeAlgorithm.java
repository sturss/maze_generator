package sviatoslav.mazes;

import sviatoslav.enums.Side;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.Random;

class RecursiveDivisionMazeAlgorithm implements MazeAlgorithm {
    private int time;
    @Override
    public void createMaze(Cell[][] cells, int rows, int cols) {
        time = 50;
        for (int i =0; i < rows; i++) {
            for(int j =0; j < cols; j++) {
                if (i < rows-1) {
                    cells[i][j].visitFrom(Side.BOTTOM_SIDE);
                    cells[i+1][j].getVisitedFrom(Side.TOP_SIDE);
                }
                if (j < cols-1) {
                    cells[i][j].visitFrom(Side.RIGHT_SIDE);
                    cells[i][j+1].getVisitedFrom(Side.LEFT_SIDE);
                }
                cells[i][j].setVisitedColor();
            }
        }
        createM(cells, 0, 0, cols-1, rows-1, 0);
        Random enter_exit = new Random();
        cells[enter_exit.nextInt(rows-1)][0].getVisitedFrom(Side.LEFT_SIDE);
        cells[enter_exit.nextInt(rows-1)][cols-1].getVisitedFrom(Side.RIGHT_SIDE);
    }

    private void createM(Cell[][] cells, int left_col_bound, int top_row_bound,
                         int right_col_bound, int bottom_row_bound, int turn) {
        if(right_col_bound - left_col_bound >= 1 && bottom_row_bound - top_row_bound >= 1) {
            this.time += 200;
            Random rand = new Random();
            PauseTransition drawingPause  = new PauseTransition(Duration.millis(time));
            int row = top_row_bound + rand.nextInt(bottom_row_bound - top_row_bound + turn) ;
            int col = left_col_bound + rand.nextInt(right_col_bound- left_col_bound + 1 - turn) ;

            if(turn == 0) {
                drawingPause.setOnFinished(event -> {
                    for (int i = left_col_bound; i <= right_col_bound; i++) {
                        cells[row][i].buildWall(Side.BOTTOM_SIDE);
                        cells[row + 1][i].buildWall(Side.TOP_SIDE);
                    }
                    cells[row][col].visitFrom(Side.BOTTOM_SIDE);
                    cells[row + 1][col].getVisitedFrom(Side.TOP_SIDE);
                });
                drawingPause.play();
                createM(cells, left_col_bound, top_row_bound, right_col_bound, row, 1);
                createM(cells, left_col_bound, row+1, right_col_bound, bottom_row_bound, 1);
            }
            else {
                drawingPause.setOnFinished(event -> {
                            for (int i = top_row_bound; i <= bottom_row_bound; i++) {
                                cells[i][col].buildWall(Side.RIGHT_SIDE);
                                cells[i][col + 1].buildWall(Side.LEFT_SIDE);
                            }
                            cells[row][col].visitFrom(Side.RIGHT_SIDE);
                            cells[row][col+1].getVisitedFrom(Side.LEFT_SIDE);
                        });
                drawingPause.play();
                createM(cells, left_col_bound, top_row_bound, col, bottom_row_bound, 0);
                createM(cells, col+1, top_row_bound, right_col_bound, bottom_row_bound, 0);
            }
        }
    }
}