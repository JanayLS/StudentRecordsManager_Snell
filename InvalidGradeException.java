/**
 * Custom exception class for handling invalid grade values.
 * This demonstrates how to create a custom exception in Java.
 * 
 * Custom exceptions allow you to create application-specific error types
 * that provide more meaningful error handling than generic exceptions.
 */
public class InvalidGradeException extends Exception {
    /**
     * TODO: Implement constructor that accepts an error message
     * 
     * @param message The detailed message explaining why the exception occurred
     * 
     * This constructor will allow users of this exception to provide
     * specific details about what went wrong with the grade value.
     */
    public InvalidGradeException(String message) {
        super(message);
    }
}