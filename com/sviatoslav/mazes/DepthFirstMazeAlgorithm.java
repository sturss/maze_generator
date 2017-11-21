package sviatoslav.mazes;

import sviatoslav.enums.Side;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


// Depth-First Search Algorithm class for Maze generation.
// Implemented in a while loop with the use of stack.
class DepthFirstMazeAlgorithm implements MazeAlgorithm {
    // When transitionCounter reaches value of the iterationCounter
    // and isDone is set to True maze is considered finished.
    private int iterationCounter = 0;
    private int transitionCounter = 0;
    private boolean isDone;
    @Override
    public void generateMaze(Maze maze) {
        long time = 20; // Time to start thread for drawing single cell.
        Side side; // Variable to store randomly chosen wall.
        Random rand = new Random();
        Stack<Point> stack = new Stack<>(); // Contains all cells of maze that been visited once.
        ArrayList<Side> sides = new ArrayList<>(4);

        // Random entry cell and starting point.
        int row = rand.nextInt(maze.getRows() - 2) + 1;
        int col = 0;
        stack.push(new Point(row, col));

        maze.setEnter(row ,col);
        maze.getEnter().getVisited();
        maze.getEnter().deleteWall(Side.LEFT_SIDE);
        maze.getEnter().setVisitedColor();

        // Random exit cell.
        maze.setExit(rand.nextInt(maze.getRows() - 1), maze.getCols() - 1);
        maze.getExit().deleteWall(Side.RIGHT_SIDE);


        while (true) {
            time += 20;
            PauseTransition drawingThread = new PauseTransition(Duration.millis(time));

            // If there are no not visited neighbour cells, algorithm returns
            // to previous cell by popping last cell added to stack.
            while (!isNotVisitedNeighbor(maze, sides, row, col)){
                stack.pop();
                // If stack is empty then algorithm returned to the first cell and generation is finished,
                if (stack.isEmpty()) {
                    isDone = true;
                    return;
                }
                row = stack.lastElement().x;
                col = stack.lastElement().y;
            }

            side = sides.get(rand.nextInt(sides.size()));

            final int[] args = {row, col};
            final Side s = side;

            if (side == Side.TOP_SIDE) {
                row -= 1;
            } else if (side == Side.RIGHT_SIDE) {
                col += 1;
            } else if (side == Side.BOTTOM_SIDE) {
                row += 1;
            } else if (side == Side.LEFT_SIDE) {
                col -= 1;
            }

            final int[] args2 = {row, col};

            // Draws visited cell in separate thread.
            // Increments transitionCounter and checks if it reaches iterationCounter.
            // If it does, sets Maze state to created.
            drawingThread.setOnFinished(event -> {
                maze.getCell(args[0], args[1]).deleteWall(s);
                maze.getCell(args2[0], args2[1]).deleteWall(Side.from(3-s.getValue()));
                maze.getCell(args2[0], args2[1]).setVisitedColor();
                maze.getCell(args2[0], args2[1]).update();
                maze.getCell(args[0], args[1]).update();
                transitionCounter += 1;
                if(isDone && iterationCounter == transitionCounter) {
                    maze.setCreated(true);
                }
            });

            maze.getCell(row, col).getVisited();
            stack.push(new Point(row, col));

            sides.clear();
            iterationCounter += 1;
            drawingThread.play();
        }
    }

    // The function checks if there are not visited neighbour cells.
    private boolean isNotVisitedNeighbor(Maze maze, ArrayList<Side> sides, int row, int col) {
        if(col > 0)
            if(!maze.getCell(row, col-1).isVisited())
                sides.add(Side.LEFT_SIDE);
        if(row > 0)
            if(!maze.getCell(row-1, col).isVisited())
                sides.add(Side.TOP_SIDE);
        if(col < maze.getCols()-1)
            if(!maze.getCell(row, col+1).isVisited())
                sides.add(Side.RIGHT_SIDE);
        if(row < maze.getRows()-1)
            if(!maze.getCell(row+1, col).isVisited())
                sides.add(Side.BOTTOM_SIDE);
        return !sides.isEmpty();
    }
}
