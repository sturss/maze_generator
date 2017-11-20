package sviatoslav.exceptions;

public class MazeSizeException extends Exception {
    public MazeSizeException() { super(); }
    public MazeSizeException(String message) { super(message); }
    public MazeSizeException(String message, Throwable cause) { super(message, cause); }
    public MazeSizeException(Throwable cause) { super(cause); }
}
