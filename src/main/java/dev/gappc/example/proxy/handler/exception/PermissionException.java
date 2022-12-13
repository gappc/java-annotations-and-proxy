package dev.gappc.example.proxy.handler.exception;

/**
 * RuntimeException for permission errors.
 */
public class PermissionException extends RuntimeException {

    public PermissionException(String message) {
        super(message);
    }

}
