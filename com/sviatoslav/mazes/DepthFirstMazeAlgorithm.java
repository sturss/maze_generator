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
    @Override
    public void createMaze(Cell[][] cells, int rows, int cols) {
        Random rand = new Random();
        Stack<Point> stack = new Stack<>();
        Side side;
        ArrayList<Side> sides = new ArrayList<>(4);
        int row = rand.nextInt(rows - 2) + 1;
        int col = 0;
        Point start = new Point(row, col);
        Point end = null;
        cells[row][col].getVisitedFrom(Side.LEFT_SIDE);
        cells[row][col].setVisitedColor();
        cells[rand.nextInt(rows - 1)][cols - 1].visitFrom(Side.RIGHT_SIDE);
        stack.push(new Point(row, col));
        long time = 20;
        while (true) {
            time += 20;
            PauseTransition visitFrom1 = new PauseTransition(Duration.millis(time));
            PauseTransition getVisited1 = new PauseTransition(Duration.millis(time));

            while (!checkSides(cells, rows, cols, sides, row, col)){
                stack.pop();
                if (stack.isEmpty()) {
                    return;
                }
                row = stack.lastElement().x;
                col = stack.lastElement().y;
            }

            side = sides.get(rand.nextInt(sides.size()));

            int[] args = {row, col};
            Side s = side;
            visitFrom1.setOnFinished(event -> cells[args[0]][args[1]].visitFrom(s));

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
            cells[row][col].getVisitedFrom(Side.from(3 - side.getValue()));

            int[] args2 = {row, col};
            getVisited1.setOnFinished(event -> cells[args2[0]][args2[1]].setVisitedColor());

            sides.clear();
            ParallelTransition transitions = new ParallelTransition(visitFrom1, getVisited1);
            transitions.play();
        }
    }

    private boolean checkSides(Cell[][] cells, int rows, int cols, ArrayList<Side> sides, int row, int col) {
        if(col > 0)
            if(!cells[row][col-1].isVisited())
                sides.add(Side.LEFT_SIDE);
        if(row > 0)
            if(!cells[row-1][col].isVisited())
                sides.add(Side.TOP_SIDE);
        if(col < cols -1)
            if(!cells[row][col+1].isVisited())
                sides.add(Side.RIGHT_SIDE);
        if(row < rows -1)
            if(!cells[row+1][col].isVisited())
                sides.add(Side.BOTTOM_SIDE);
        return !sides.isEmpty();
    }
}
