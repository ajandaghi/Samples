package exception;

public class ProcessException extends Exception{
   public ProcessException(String errorBundle,String message) {
        super(errorBundle);
    }
    public ProcessException(String errorBundle) {
        super(errorBundle);
    }

}
