package com.sviatoslav.mazes;

public class MazeAlgorithmFactory {
    public static MazeAlgorithm getMazeAlgorithm(com.sviatoslav.enums.MazeAlgorithm mazeAlgorithm) {
        if(mazeAlgorithm == com.sviatoslav.enums.MazeAlgorithm.DEPTH_FIRST_SEARCH) {
            return new DepthFirstMazeAlgorithm();
        }
        else if(mazeAlgorithm == com.sviatoslav.enums.MazeAlgorithm.RECURSIVE_DIVIDE_SEARCH) {
            return new RecursiveDivisionMazeAlgorithm();
        }
        else {
            throw new IllegalArgumentException("Algorithm does't exist");
        }
    }
}
