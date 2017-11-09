package sviatoslav.mazes;

import sviatoslav.enums.Side;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

class DepthFirstMazeAlgorithm implements MazeAlgorithm {
    private int iterationCounter = 0;
    private int transitionCounter = 0;
    private boolean isDone;
    @Override
    public void createMaze(Maze maze) {
        Random rand = new Random();
        Stack<Point> stack = new Stack<>();
        Side side;
        ArrayList<Side> sides = new ArrayList<>(4);
        int row = rand.nextInt(maze.getRows() - 2) + 1;
        int col = 0;

        maze.setEnter(row ,col);
        maze.getEnter().getVisited();
        maze.getEnter().deleteWall(Side.LEFT_SIDE);
        maze.getEnter().setVisitedColor();
        maze.setExit(rand.nextInt(maze.getRows() - 1), maze.getCols() - 1);
        maze.getExit().deleteWall(Side.RIGHT_SIDE);

        stack.push(new Point(row, col));
        long time = 20;
        while (true) {
            time += 20;
            PauseTransition visitFrom1 = new PauseTransition(Duration.millis(time));
            PauseTransition getVisited1 = new PauseTransition(Duration.millis(time));

            while (!checkSides(maze, sides, row, col)){
                stack.pop();
                if (stack.isEmpty()) {
                    isDone =true;
                    return;
                }
                row = stack.lastElement().x;
                col = stack.lastElement().y;
            }

            side = sides.get(rand.nextInt(sides.size()));

            int[] args = {row, col};
            Side s = side;

            visitFrom1.setOnFinished(event -> {
                maze.getCell(args[0], args[1]).deleteWall(s);
                maze.getCell(args[0], args[1]).update();
            });

            if (side == Side.TOP_SIDE) {
                row -= 1;
            } else if (side == Side.RIGHT_SIDE) {
                col += 1;
            } else if (side == Side.BOTTOM_SIDE) {
                row += 1;
            } else if (side == Side.LEFT_SIDE) {
                col -= 1;
            }
            stack.push(new Point(row, col));

            int[] args2 = {row, col};
            maze.getCell(row, col).getVisited();
            getVisited1.setOnFinished(event -> {
                maze.getCell(args2[0], args2[1]).deleteWall(Side.from(3-s.getValue()));
                maze.getCell(args2[0], args2[1]).setVisitedColor();
                maze.getCell(args2[0], args2[1]).update();
                transitionCounter += 1;
                if(isDone && iterationCounter == transitionCounter) {
                    maze.setCreated(true);
                }
            });

            sides.clear();
            iterationCounter += 1;
            ParallelTransition transitions = new ParallelTransition(visitFrom1, getVisited1);
            transitions.play();
        }
    }

    private boolean checkSides(Maze maze, ArrayList<Side> sides, int row, int col) {
        if(col > 0)
            if(!maze.getCell(row, col-1).isVisited())
                sides.add(Side.LEFT_SIDE);
        if(row > 0)
            if(!maze.getCell(row-1, col).isVisited())
                sides.add(Side.TOP_SIDE);
        if(col < maze.getCols() -1)
            if(!maze.getCell(row, col+1).isVisited())
                sides.add(Side.RIGHT_SIDE);
        if(row < maze.getRows() -1)
            if(!maze.getCell(row+1, col).isVisited())
                sides.add(Side.BOTTOM_SIDE);
        return !sides.isEmpty();
    }
}
