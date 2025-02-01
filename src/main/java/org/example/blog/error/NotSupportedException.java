package org.example.blog.error;

public class NotSupportedException extends RuntimeException {

    public NotSupportedException(String message) {
        super(message);
    }
}
