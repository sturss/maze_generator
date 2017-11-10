package sviatoslav.exceptions;

public class MazeGenerationNotFinished extends Exception {
    public MazeGenerationNotFinished() { super(); }
    public MazeGenerationNotFinished(String message) { super(message); }
    public MazeGenerationNotFinished(String message, Throwable cause) { super(message, cause); }
    public MazeGenerationNotFinished(Throwable cause) { super(cause); }
}
