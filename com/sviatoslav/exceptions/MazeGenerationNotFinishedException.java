package sviatoslav.exceptions;

public class MazeGenerationNotFinishedException extends Exception {
    public MazeGenerationNotFinishedException() { super(); }
    public MazeGenerationNotFinishedException(String message) { super(message); }
    public MazeGenerationNotFinishedException(String message, Throwable cause) { super(message, cause); }
    public MazeGenerationNotFinishedException(Throwable cause) { super(cause); }
}
