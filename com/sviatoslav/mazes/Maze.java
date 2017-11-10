package sviatoslav.mazes;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import sviatoslav.enums.Side;
import sviatoslav.exceptions.MazeGenerationNotFinished;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Maze extends GridPane {
    private int rows;
    private int cols;
    private Point enter;
    private Point exit;
    private Cell[][] cells;
    private boolean created = false;
    private MazeAlgorithm algorithm;

    public Maze(MazeAlgorithm algorithm, int rows, int cols){
        if (rows < 3 || cols < 3 || rows > 100 || cols > 100)
            throw new IllegalArgumentException("Wrong values of maze size");
        this.algorithm = algorithm;
        cells = new Cell[rows][cols];
        int size = 10;
        int[][] sizes = {{10, 7, 52}, {19, 11, 42}, {22, 13, 33}, {26, 14, 30},
                {31, 18, 25}, {39, 23, 20}, {52, 29, 15}};
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
        algorithm.createMaze(this);
    }

    private void checkNeighboursLength(int row, int col) {
        int[] neighbours = {0, 0, 0 , 0};
        if(col > 0)
            if(!cells[row][col].isWall(Side.LEFT_SIDE) && cells[row][col-1].getLength() == 0) {
                cells[row][col-1].setLength(cells[row][col].getLength() + 1);
                cells[row][col-1].drawLength();
                neighbours[0] = 1;
            }
        if(row > 0)
            if(!cells[row][col].isWall(Side.TOP_SIDE) && cells[row-1][col].getLength() == 0) {
                cells[row-1][col].setLength(cells[row][col].getLength() + 1);
                cells[row-1][col].drawLength();
                neighbours[1] = 1;
            }
        if(col < cols-1)
            if(!cells[row][col].isWall(Side.RIGHT_SIDE) && cells[row][col+1].getLength() == 0) {
                cells[row][col+1].setLength(cells[row][col].getLength() + 1);
                cells[row][col+1].drawLength();
                neighbours[2] = 1;
            }
        if(row < rows-1)
            if(!cells[row][col].isWall(Side.BOTTOM_SIDE) && cells[row+1][col].getLength() == 0) {
                cells[row+1][col].setLength(cells[row][col].getLength() + 1);
                cells[row+1][col].drawLength();
                neighbours[3] = 1;
            }

        if(neighbours[0] == 1)
            checkNeighboursLength(row, col-1);
        if(neighbours[1] == 1)
            checkNeighboursLength(row-1, col);
        if(neighbours[2] == 1)
            checkNeighboursLength(row, col+1);
        if(neighbours[3] == 1)
            checkNeighboursLength(row+1, col);

    }

    public void findShortestWay() throws MazeGenerationNotFinished {
        if (created) {
            getEnter().setLength(1);
            getEnter().drawLength();
            checkNeighboursLength(enter.x, enter.y);
        } else {
            throw new MazeGenerationNotFinished("Maze is not created yet");
        }
    }

    void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isCreated() {
        return created;
    }

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }

    Cell getCell(int row, int col) {
        return cells[row][col];
    }

    void setEnter(int row, int col) {
        this.enter = new Point(row, col);
    }

    public Cell getEnter() {
        return cells[enter.x][enter.y];
    }

    void setExit(int row, int col) {
        this.exit = new Point(row, col);
    }

    public Cell getExit() {
        return cells[exit.x][exit.y];
    }

    void destroyWalls() {
        for (int i = 0; i < rows; i++) {
            for(int j =0; j < cols; j++) {
                if (i < rows-1) {
                    cells[i][j].deleteWall(Side.BOTTOM_SIDE);
                    cells[i+1][j].deleteWall(Side.TOP_SIDE);
                    cells[i+1][j].update();
                }
                if (j < cols-1) {
                    cells[i][j].deleteWall(Side.RIGHT_SIDE);
                    cells[i][j+1].deleteWall(Side.LEFT_SIDE);
                    cells[i][j+1].update();
                }
                cells[i][j].setVisitedColor();
                cells[i][j].update();
            }
        }
    }

    void buildWalls() {
        for(int i =0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                cells[i][j].buildWall(Side.BOTTOM_SIDE);
                cells[i][j].buildWall(Side.TOP_SIDE);
                cells[i][j].buildWall(Side.RIGHT_SIDE);
                cells[i][j].buildWall(Side.LEFT_SIDE);
                cells[i][j].setUnvisitedColor();
            }
        }
    }

    @FXML
    public void saveAsPng() {
        WritableImage image = snapshot(new SnapshotParameters(), null);
        File file = new File("maze" + new SimpleDateFormat("ss-mm-hh-dd-MM-yyyy").format(new Date()) + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}