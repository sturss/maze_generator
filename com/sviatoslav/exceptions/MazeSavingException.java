package sviatoslav.exceptions;

public class MazeSavingException extends Exception {
    public MazeSavingException() { super(); }
    public MazeSavingException(String message) { super(message); }
    public MazeSavingException(String message, Throwable cause) { super(message, cause); }
    public MazeSavingException(Throwable cause) { super(cause); }
}
