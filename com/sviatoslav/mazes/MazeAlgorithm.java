package sviatoslav.mazes;


//Interface for maze generation algorithms
//It has to be a strategy pattern but who knows.
public interface MazeAlgorithm {
    void createMaze(Cell[][] cells, int rows, int cols);
}

