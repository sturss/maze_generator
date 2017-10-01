package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.util.Random;
import java.util.Vector;


public class Maze extends GridPane {
    private Integer size_x;
    private Integer size_y;
    Cell[][] cells;

    public  Maze(int y_num, int x_num) {
        cells = new Cell[y_num][x_num];
        setPadding( new Insets(10, 10, 10,10));

        size_x = x_num;
        size_y = y_num;
        for(int i =0;i < y_num; i++) {
            for (int j = 0; j < x_num; j++) {
                Cell c = new Cell(600 / x_num , 400/y_num);
                add(c, j, i);
                cells[i][j] = c;
            }
        }
        recursive_maze();
    }

    public void recursive_maze() {
        Random rand = new Random();
        Integer y_cell = rand.nextInt(size_y-2) + 1;
        Integer x_cell = 0;
        Vector<Integer> sides = new Vector(0, 4);
        sides.add(Constants.TOP_SIDE);
        sides.add(Constants.RIGHT_SIDE);
        sides.add(Constants.BOTTOM_SIDE);
        cells[y_cell][x_cell].getVisited(Constants.LEFT_SIDE);
        while(sides.size() > 0) {
            Integer side = sides.get(rand.nextInt(sides.size()));
            System.out.print("Side" + side);
            cells[y_cell][x_cell].visitFrom(side);
            if(side.equals(Constants.TOP_SIDE)) {
                System.out.print("top");
                y_cell -= 1;
            }
            else if(side.equals(Constants.RIGHT_SIDE)) {
                System.out.print("r");
                x_cell += 1;
            }
            else if(side.equals(Constants.BOTTOM_SIDE)) {
                System.out.print("b");
                y_cell += 1;
            }
            else if(side.equals(Constants.LEFT_SIDE)) {
                System.out.print("l");
                x_cell -= 1;
            }
            cells[y_cell][x_cell].getVisited(3-side);
            sides.clear();

            if(x_cell > 0)
                if(!cells[y_cell][x_cell-1].isVisited())
                    sides.add(Constants.LEFT_SIDE);
            if(y_cell > 0)
                if(!cells[y_cell-1][x_cell].isVisited())
                    sides.add(Constants.TOP_SIDE);
            if(x_cell < size_x-1)
                if(!cells[y_cell][x_cell+1].isVisited())
                    sides.add(Constants.RIGHT_SIDE);
            if(y_cell < size_y-1)
                if(!cells[y_cell+1][x_cell].isVisited())
                    sides.add(Constants.BOTTOM_SIDE);
        }
        cells[y_cell][x_cell].Color();
    }
}