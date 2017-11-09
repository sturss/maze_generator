package sviatoslav.mazes;

import sviatoslav.enums.Side;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.Random;

class RecursiveDivisionMazeAlgorithm implements MazeAlgorithm {
    private int time;
    private int functionCounter = 0;
    private int transitionCounter = 0;
    private boolean isDone = false;
    @Override
    public void createMaze(Maze maze) {
        time = 10;
        maze.destroyWalls();
        createM(maze, 0, 0, maze.getCols()-1,
                maze.getRows()-1, 0);

        Random random = new Random();
        maze.setEnter(random.nextInt(maze.getRows()-1), 0);
        maze.getEnter().deleteWall(Side.LEFT_SIDE);
        maze.getEnter().update();

        maze.setExit(random.nextInt(maze.getRows()-1), maze.getCols()-1);
        maze.getExit().deleteWall(Side.RIGHT_SIDE);
        maze.getExit().update();

        isDone = true;
    }

    private void createM(Maze maze, int leftColBound, int topRowBound,
                         int rightColBound, int bottomRowBound, int turn) {
        if(rightColBound - leftColBound >= 1 && bottomRowBound - topRowBound >= 1) {
            this.time += 100;
            Random rand = new Random();
            PauseTransition buildingPause  = new PauseTransition(Duration.millis(time));
            int row = topRowBound + rand.nextInt(bottomRowBound - topRowBound + turn) ;
            int col = leftColBound + rand.nextInt(rightColBound- leftColBound + 1 - turn) ;

            if(turn == 0) {
                buildingPause.setOnFinished(event -> {
                    transitionCounter += 1;
                    for (int i = leftColBound; i <= rightColBound; i++) {
                        maze.getCell(row, i).buildWall(Side.BOTTOM_SIDE);
                        maze.getCell(row+1, i).buildWall(Side.TOP_SIDE);
                    }
                    maze.getCell(row, col).deleteWall(Side.BOTTOM_SIDE);
                    maze.getCell(row+1, col).deleteWall(Side.TOP_SIDE);
                    for (int i = leftColBound; i <= rightColBound; i++) {
                        maze.getCell(row, i).update();
                        maze.getCell(row + 1, i).update();
                    }
                    maze.getCell(row, col).update();
                    maze.getCell(row+1, col).update();
                    if(isDone && functionCounter == transitionCounter) {
                        maze.setCreated(true);
                    }
                });
                functionCounter += 1;
                buildingPause.play();
                createM(maze, leftColBound, topRowBound, rightColBound, row, 1);
                createM(maze, leftColBound, row+1, rightColBound, bottomRowBound, 1);
            }
            else {
                buildingPause.setOnFinished(event -> {
                    transitionCounter += 1;
                    for (int i = topRowBound; i <= bottomRowBound; i++) {
                        maze.getCell(i, col).buildWall(Side.RIGHT_SIDE);
                        maze.getCell(i, col + 1).buildWall(Side.LEFT_SIDE);
                    }
                    maze.getCell(row, col).deleteWall(Side.RIGHT_SIDE);
                    maze.getCell(row, col+1).deleteWall(Side.LEFT_SIDE);

                    for (int i = topRowBound; i <= bottomRowBound; i++) {
                        maze.getCell(i, col).update();
                        maze.getCell(i, col + 1).update();
                    }
                    maze.getCell(row, col).update();
                    maze.getCell(row, col+1).update();
                    if(isDone && functionCounter == transitionCounter) {
                        maze.setCreated(true);
                    }
                });
                functionCounter += 1;
                buildingPause.play();
                createM(maze, leftColBound, topRowBound, col, bottomRowBound, 0);
                createM(maze, col+1, topRowBound, rightColBound, bottomRowBound, 0);
            }
        }
    }
}
