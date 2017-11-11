package sviatoslav.exceptions;

public class MazeSizeOutOfRangeException extends Exception {
    public MazeSizeOutOfRangeException() { super(); }
    public MazeSizeOutOfRangeException(String message) { super(message); }
    public MazeSizeOutOfRangeException(String message, Throwable cause) { super(message, cause); }
    public MazeSizeOutOfRangeException(Throwable cause) { super(cause); }
}
