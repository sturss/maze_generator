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
        for (int i = 0; i < rows; i++) {
            for(int j =0; j < cols; j++) {
                if (i < rows-1) {
                    cells[i][j].deleteWall(Side.BOTTOM_SIDE);
                    cells[i+1][j].deleteWall(Side.TOP_SIDE);
                }
                if (j < cols-1) {
                    cells[i][j].deleteWall(Side.RIGHT_SIDE);
                    cells[i][j+1].deleteWall(Side.LEFT_SIDE);
                }
                cells[i][j].setVisitedColor();
            }
        }
        createM(cells, 0, 0, cols-1, rows-1, 0);
        Random random = new Random();
        cells[random.nextInt(rows-1)][0].deleteWall(Side.LEFT_SIDE);
        cells[random.nextInt(rows-1)][cols-1].deleteWall(Side.RIGHT_SIDE);
    }

    private void createM(Cell[][] cells, int leftColBound, int topRowBound,
                         int rightColBound, int bottomRowBound, int turn) {
        if(rightColBound - leftColBound >= 1 && bottomRowBound - topRowBound >= 1) {
            this.time += 200;
            Random rand = new Random();
            PauseTransition drawingPause  = new PauseTransition(Duration.millis(time));
            int row = topRowBound + rand.nextInt(bottomRowBound - topRowBound + turn) ;
            int col = leftColBound + rand.nextInt(rightColBound- leftColBound + 1 - turn) ;

            if(turn == 0) {
                drawingPause.setOnFinished(event -> {
                    for (int i = leftColBound; i <= rightColBound; i++) {
                        cells[row][i].buildWall(Side.BOTTOM_SIDE);
                        cells[row + 1][i].buildWall(Side.TOP_SIDE);
                    }
                    cells[row][col].deleteWall(Side.BOTTOM_SIDE);
                    cells[row + 1][col].deleteWall(Side.TOP_SIDE);
                });
                drawingPause.play();
                createM(cells, leftColBound, topRowBound, rightColBound, row, 1);
                createM(cells, leftColBound, row+1, rightColBound, bottomRowBound, 1);
            }
            else {
                drawingPause.setOnFinished(event -> {
                            for (int i = topRowBound; i <= bottomRowBound; i++) {
                                cells[i][col].buildWall(Side.RIGHT_SIDE);
                                cells[i][col + 1].buildWall(Side.LEFT_SIDE);
                            }
                            cells[row][col].deleteWall(Side.RIGHT_SIDE);
                            cells[row][col+1].deleteWall(Side.LEFT_SIDE);
                        });
                drawingPause.play();
                createM(cells, leftColBound, topRowBound, col, bottomRowBound, 0);
                createM(cells, col+1, topRowBound, rightColBound, bottomRowBound, 0);
            }
        }
    }
}
